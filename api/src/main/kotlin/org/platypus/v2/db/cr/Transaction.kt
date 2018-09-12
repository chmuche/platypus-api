package org.platypus.v2.db.cr

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.database.TransactionMode
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.DbFieldConverter
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

interface Transaction : Connection {
    val mode: TransactionMode
    val dialect: DbDialect
    val dbName: String
    val executor: StatementExecutor
}

class TransactionStat : StatementInterceptor {
    private val map = EnumMap<StatementType, Int>(StatementType::class.java)

    fun reset() {
        map.clear()
    }

    operator fun get(type: StatementType): Int = map[StatementType.SELECT] ?: 0

    val nbSelect: Int get() = map[StatementType.SELECT] ?: 0
    val nbDelete: Int get() = map[StatementType.DELETE] ?: 0
    val nbInsert: Int get() = map[StatementType.INSERT] ?: 0
    val nbUpdate: Int get() = map[StatementType.UPDATE] ?: 0

    override fun afterExecution(contexts: List<StatementContext>, executedStatement: Statement, delta: Long) {
        contexts.forEach {
            map.compute(it.statement.type, { _, nb -> (nb ?: 0) + 1 })
        }
    }
}

class StatementExecutor(
        tr: Transaction,
        interceptors: Set<StatementInterceptor> = emptySet(),
        sqlLoggers: Set<SqlLogger> = emptySet()
) : Transaction by tr {

    val monitor = StatementMonitor()
    val logger = CompositeSqlLogger()

    init {
        interceptors.forEach { monitor.register(it) }
        sqlLoggers.forEach { logger.register(it) }

    }

    fun <T : Any, R> execute(stmt: SqlStatement<T>, body: SqlStatement<T>.(T) -> R): R? {
        val answer = stmt.executeIn()
        return answer.first?.let { stmt.body(it) }
    }

    private fun <T : Any> SqlStatement<T>.executeIn(): Pair<T?, List<StatementContext>> {
        val start = System.currentTimeMillis()
        val arguments = arguments()
        val contexts = createStatementContext(arguments)
        monitor.notifyBeforeExecution(contexts)
        for (context in contexts) {
        }
        val result: T?
        try {
            val statement = prepareStatement()
            contexts.forEachIndexed { _, context ->
                statement.fillParameters(context.args)
                // REVIEW
                if (contexts.size > 1 || isAlwaysBatch) statement.addBatch()
            }
            result = statement.executeInternal()
            val delta = System.currentTimeMillis() - start
            monitor.notifyAfterExecution(contexts, statement, delta)
        } catch (e: Exception) {
            monitor.notifyOnError(contexts, e)
            throw e
        }
        return result to contexts
    }

    private fun <T : Any> SqlStatement<T>.createStatementContext(arguments: Iterable<Iterable<Pair<DbFieldConverter, Any?>>>): List<StatementContext> {
        return if (arguments.count() > 0) {
            arguments.map { args ->
                val context = StatementContext(this, args)
                context
            }
        } else {
            val context = StatementContext(this, emptyList())
            listOf(context)
        }
    }

    fun PreparedStatement.fillParameters(args: Iterable<Pair<DbFieldConverter, Any?>>): Int {
        args.forEachIndexed { index, (c, v) ->
            c.setParameter(this, index + 1, c.convertToDB(v))
        }

        return args.count() + 1
    }

    fun <T : Any> nativeWithResultSet(sql: String, transform: (ResultSet) -> T): T? {
        val start = System.currentTimeMillis()
        val stmt = createStatement()
        monitor.notifyBeforeExecution(emptyList())
        try {
            stmt.execute(sql)
            val delta = System.currentTimeMillis() - start
            monitor.notifyAfterExecution(emptyList(), stmt, delta)
        } catch (e: Exception) {
            monitor.notifyOnError(emptyList(), e)
            throw e
        } finally {
            stmt.closeOnCompletion()
        }
        return stmt.resultSet?.let { transform(it) }
    }

    fun nativeWithNb(sql: String): Int {
        val start = System.currentTimeMillis()
        val stmt = createStatement()
        monitor.notifyBeforeExecution(emptyList())
        try {
            stmt.execute(sql)
            val delta = System.currentTimeMillis() - start
            monitor.notifyAfterExecution(emptyList(), stmt, delta)
        } catch (e: Exception) {
            monitor.notifyOnError(emptyList(), e)
            throw e
        }
        val res = stmt.updateCount
        stmt.close()
        return res
    }


}

enum class StatementGroup {
    DDL, DML
}

enum class StatementType(val group: StatementGroup) {
    INSERT(StatementGroup.DML), UPDATE(StatementGroup.DML), DELETE(StatementGroup.DML), SELECT(StatementGroup.DML),
    CREATE(StatementGroup.DDL), ALTER(StatementGroup.DDL), TRUNCATE(StatementGroup.DDL), DROP(StatementGroup.DDL),
    GRANT(StatementGroup.DDL), OTHER(StatementGroup.DDL)
}

interface SqlStatement<T : Any> {

    fun prepareSQL(): String
    fun prepareStatement(): PreparedStatement
    fun PreparedStatement.executeInternal(): T?
    val type: StatementType
    val targets: List<BaseModel<*>>
    fun arguments(): Iterable<Iterable<Pair<DbFieldConverter, Any?>>>
    val isAlwaysBatch: Boolean
        get() = false

    fun execute(tr: Transaction): T?
}
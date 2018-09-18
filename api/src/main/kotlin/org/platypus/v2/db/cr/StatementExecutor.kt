package org.platypus.v2.db.cr

import org.platypus.v2.model.field.api.DbFieldConverter
import java.sql.PreparedStatement
import java.sql.ResultSet

class StatementExecutor(
        tr: Transaction,
        interceptors: Set<StatementInterceptor> = emptySet(),
        sqlLoggers: Set<SqlLogger> = emptySet()
) : Transaction by tr {

    val monitor = StatementMonitor()


    init {
        val logger = CompositeSqlLogger()
        interceptors.forEach { monitor.register(it) }
        sqlLoggers.forEach { logger.register(it) }
        monitor.register(logger)

    }

    fun <T> execute(stmt: SqlStatement<T>): T? = execute(stmt, { it })

    fun <T, R> execute(stmt: SqlStatement<T>, body: SqlStatement<T>.(T) -> R): R? {
        val answer = stmt.executeIn()
        return answer.first?.let { stmt.body(it) }
    }

    private fun <T> SqlStatement<T>.executeIn(): Pair<T?, List<StatementContext>> {
        val start = System.currentTimeMillis()
        val arguments = this.arguments()
        val contexts = this.createStatementContext(arguments)
        monitor.notifyBeforeExecution(contexts)
        val result: T?
        try {
            val sql = prepareSQL()
            val statement = this.prepared(this@StatementExecutor, sql)
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

    private fun <T> SqlStatement<T>.createStatementContext(arguments: Iterable<Iterable<Pair<DbFieldConverter, Any?>>>): List<StatementContext> {
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
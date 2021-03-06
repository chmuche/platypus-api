package org.platypus.v2.db.cr

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.database.TransactionMode
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.record.one.RecordBuilder
import java.sql.Connection
import java.sql.Statement
import java.util.*

interface Transaction : Connection {
    val mode: TransactionMode
    val dialect: DbDialect
    val dbName: String
    val executor: StatementExecutor
    val cache: TransactionRecordCache
}

interface TransactionRecordCache {
    operator fun set(cacheRef: Pair<BaseModel<*>, Int>, data: RecordBuilder<*>)
    operator fun get(cacheRef: Pair<BaseModel<*>, Int>): Map<BaseField<*, *>, Any?>
}

class DefaultTransactionCache : TransactionRecordCache {
    private val cache: MutableMap<BaseModel<*>, MutableMap<Int, MutableMap<BaseField<*, *>, Any?>>> = HashMap()
    override fun set(cacheRef: Pair<BaseModel<*>, Int>, data: RecordBuilder<*>) {
        cache.getOrPut(cacheRef.first, { HashMap() })[cacheRef.second] = data.rawData.toMutableMap()
    }

    override fun get(cacheRef: Pair<BaseModel<*>, Int>): Map<BaseField<*, *>, Any?> {
        return cache.getOrPut(cacheRef.first, { HashMap() })[cacheRef.second] ?: emptyMap()
    }
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

enum class StatementGroup {
    DDL, DML
}

enum class StatementType(val group: StatementGroup) {
    INSERT(StatementGroup.DML), UPDATE(StatementGroup.DML), DELETE(StatementGroup.DML), SELECT(StatementGroup.DML),
    CREATE(StatementGroup.DDL), ALTER(StatementGroup.DDL), TRUNCATE(StatementGroup.DDL), DROP(StatementGroup.DDL),
    GRANT(StatementGroup.DDL), OTHER(StatementGroup.DDL)
}

package org.platypus.v2.db.cr

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.DbFieldConverter
import java.sql.PreparedStatement

abstract class SqlStatement<out T>(val dialect: DbDialect, val type: StatementType, val targets: List<BaseModel<*>>) {

    abstract fun PreparedStatement.executeInternal(): T?

    abstract fun prepareSQL(): String

    abstract fun arguments(): List<List<Pair<DbFieldConverter, Any?>>>

    open fun prepared(cr:Transaction, sql: String): PreparedStatement = cr.prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS)!!

    open val isAlwaysBatch: Boolean get() = false

    fun PreparedStatement.fillParameters(args: Iterable<Pair<DbFieldConverter, Any?>>): Int {
        args.forEachIndexed { index, (c, v) ->
            c.setParameter(this, index + 1, c.convertToDB(v))
        }
        return args.count() + 1
    }


}
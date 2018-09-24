package org.platypus.v2.db.cr.statements

import org.platypus.v2.db.cr.SqlStatement
import org.platypus.v2.db.cr.StatementType
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import java.util.*

/**
 * @author max
 */

abstract class UpdateBuilder<out T>(
        dialect: DbDialect,
        type: StatementType,
        targets: List<BaseModel<*>>
) : SqlStatement<T>(dialect, type, targets) {

    protected val values: MutableMap<BaseField<*, *>, Any?> = LinkedHashMap()

    open operator fun <S : Any> set(column: BaseField<*, S>, value: S) {
        forceSet(column, value)
    }

    fun containsKey(column: BaseField<*, *>): Boolean = values.containsKey(column)

    internal open fun forceSet(column: BaseField<*, *>, value: Any?) {
        if (!column.required && value == null) {
            error("Trying to forceSet null to not required column $column")
        }
        if (value != null && containsKey(column)) {
            error("$column is already initialized")
        }
        if (value != null) {
            values[column] = value
        }
    }

//    open fun <S : Any> update(column: BaseField<*,  S>, value: Expression<S>) {
//        if (values.containsKey(column)) {
//            error("$column is already initialized")
//        }
//        values[column] = value
//    }
}

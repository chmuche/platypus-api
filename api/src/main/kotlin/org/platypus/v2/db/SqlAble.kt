package org.platypus.v2.db

import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.api.DbFieldConverter
import java.util.*

interface SqlAble {

    fun toSql(dialect: DbDialect, builder: QueryBuilder): String
}

interface SqlQueryParameter {
    val prepared: Boolean
    val args: List<Pair<DbFieldConverter, Any?>>
    fun <T> registerArgument(dialect: DbDialect, column: BaseField<*, *>, argument: T): String
    fun <T> registerArgument(sqlType: DbFieldConverter, argument: T): String
    fun <T> registerArguments(sqlType: DbFieldConverter, arguments: Iterable<T>): List<String>
}

class QueryBuilder(override val prepared: Boolean = false):SqlQueryParameter {
    override val args = ArrayList<Pair<DbFieldConverter, Any?>>()

    override fun <T> registerArgument(dialect: DbDialect, column: BaseField<*, *>, argument: T): String = when (argument) {
//        is Expression<*> -> argument.accept(executor.dialect.expressionVisitor, this)
//        DefaultValueMarker -> asLiteral(column.default!!).accept(executor.dialect.expressionVisitor, this)
        else -> registerArgument(column, argument)
    }

    override fun <T> registerArgument(sqlType: DbFieldConverter, argument: T) = registerArguments(sqlType, listOf(argument)).single()

    override fun <T> registerArguments(sqlType: DbFieldConverter, arguments: Iterable<T>): List<String> {
        val argumentsAndStrings = arguments.map { it to sqlType.convertToString(it) }.sortedBy { it.second }

        return argumentsAndStrings.map {
            if (prepared) {
                args.add(sqlType to it.first)
                "?"
            } else {
                it.second
            }
        }
    }
}
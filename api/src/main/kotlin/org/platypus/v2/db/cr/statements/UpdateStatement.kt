package org.platypus.v2.db.cr.statements

import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.db.cr.StatementType
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.api.DbFieldConverter
import org.platypus.v2.utils.token
import java.sql.PreparedStatement

open class UpdateStatement(dialect: DbDialect, val table: BaseModel<*>, val where: Predicate? = null, val limit: Int? = null) :
        UpdateBuilder<Int>(dialect, StatementType.UPDATE, listOf(table)) {

    open val firstDataSet: List<Pair<BaseField<*, *>, Any?>> get() = values.toList()

    override fun PreparedStatement.executeInternal(): Int {
        if (values.isEmpty()) return 0
        return executeUpdate()
    }

    override fun prepareSQL(): String = buildString {
        val builder = QueryBuilder(true)

        token("UPDATE")
        token(dialect.identity(table))
        token("SET")
        append(firstDataSet.joinToString { (col, value) ->
            "${dialect.identity(col)}=" + builder.registerArgument(dialect, col, value)
        })

        where?.let { append(" WHERE " + it.toSql(dialect, builder)) }
        limit?.let { append(" LIMIT $it") }
    }


    override fun arguments(): List<List<Pair<DbFieldConverter, Any?>>> = QueryBuilder(true).run {
        values.forEach {
            registerArgument(dialect, it.key, it.value)
        }
        where?.toSql(dialect, this)
        if (args.isNotEmpty()) listOf(args) else emptyList()
    }

}

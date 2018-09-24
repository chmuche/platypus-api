package org.platypus.v2.db.cr.statements

import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.db.cr.SqlStatement
import org.platypus.v2.db.cr.StatementType
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.Model
import org.platypus.v2.model.field.api.DbFieldConverter
import org.platypus.v2.utils.token
import java.sql.PreparedStatement
import java.sql.Statement

class DeleteStatement (dialect:DbDialect, val model: BaseModel<*>, val where: Predicate? = null) : SqlStatement<Int>(dialect, StatementType.DELETE, listOf(model)) {

    override fun PreparedStatement.executeInternal(): Int {
        return executeUpdate()
    }

    override fun prepareSQL(): String = buildString {
        token("DELETE")
        token("FROM")
        token(dialect.identity(model))
        if (where != null) {
            token("WHERE")
            append(where.toSql(dialect, QueryBuilder(true)))
        }
    }

    override fun arguments(): List<List<Pair<DbFieldConverter, Any?>>> = QueryBuilder(true).run {
        where?.toSql(dialect, this)
        listOf(args)
    }
}
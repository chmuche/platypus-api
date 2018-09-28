package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.cr.SqlStatement
import org.platypus.v2.db.cr.StatementType
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.Alias
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.api.DbFieldConverter
import org.platypus.v2.utils.token
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectModelStatement<M : BaseModel<M>>(
        dialect: DbDialect,
        read:Set<BaseField<M, *>>,
        from: Alias<M>,
        joins: List<JoinStatementPart<*, *>>,
        orderby:

        val selectModelWhereBuilder: SelectModelWhereBuilder<M>) : SqlStatement<ResultSet>(dialect, StatementType.SELECT, listOf(from.originalModel)) {

    override fun PreparedStatement.executeInternal(): ResultSet? {
        TODO("not implemented")
    }

    override fun prepareSQL(): String = buildString {
        token("SELECT")
        token(selectModelWhereBuilder.SELECT(dialect))
        token("FROM")
        token(selectModelWhereBuilder.FROM(dialect))
        token("WHERE")
        token(selectModelWhereBuilder.WHERE(dialect, selectModelWhereBuilder.))
    }

    override fun arguments(): List<List<Pair<DbFieldConverter, Any?>>> {
        TODO("not implemented")
    }
}
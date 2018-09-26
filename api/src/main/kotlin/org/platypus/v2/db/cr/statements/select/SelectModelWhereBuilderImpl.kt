package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.Alias
import org.platypus.v2.model.AliasImpl
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.utils.enter
import org.platypus.v2.utils.token

class SelectModelWhereBuilderImpl<M : BaseModel<M>>(val from: Alias<M>) : SelectModelWhereBuilder<M>, Alias<M> by from {

    private val joins: MutableList<JoinStatementPart<*, *>> = ArrayList()

    fun MutableList<JoinStatementPart<*, *>>.addIfNotIn(e: JoinStatementPart<*, *>): Boolean {
        var result = false
        if (e !in this) {
            result = this.add(e)
        }
        return result
    }

    override fun FROM(dialect: DbDialect, predicate: Predicate) = buildString {
        token("FROM")
        token(from.tableName)
        val qb = QueryBuilder(false)
        for (join in joins) {
            append(join.toSql(dialect, qb))
        }
        enter()
        token("WHERE")
        append(predicate.toSql(dialect, qb))
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>> Many2OneField<M, M1>.join(getter: M1.() -> Many2OneField<M1, M2>): Join3<M, M1, M2> {
        return Join3(Join2(this@SelectModelWhereBuilderImpl, this), this.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>> Join3<M, M1, M2>.join(getter: M2.() -> Many2OneField<M2, M3>): Join4<M, M1, M2, M3> {
        return Join4(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>> Join4<M, M1, M2, M3>.join(getter: M3.() -> Many2OneField<M3, M4>): Join5<M, M1, M2, M3, M4> {
        return Join5(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>> Join5<M, M1, M2, M3, M4>.join(getter: M4.() -> Many2OneField<M4, M5>): Join6<M, M1, M2, M3, M4, M5> {
        return Join6(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>> Join6<M, M1, M2, M3, M4, M5>.join(getter: M5.() -> Many2OneField<M5, M6>): Join7<M, M1, M2, M3, M4, M5, M6> {
        return Join7(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>> Join7<M, M1, M2, M3, M4, M5, M6>.join(getter: M6.() -> Many2OneField<M6, M7>): Join8<M, M1, M2, M3, M4, M5, M6, M7> {
        return Join8(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>> Join8<M, M1, M2, M3, M4, M5, M6, M7>.join(getter: M7.() -> Many2OneField<M7, M8>): Join9<M, M1, M2, M3, M4, M5, M6, M7, M8> {
        return Join9(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>, M9 : BaseModel<M9>> Join9<M, M1, M2, M3, M4, M5, M6, M7, M8>.join(getter: M8.() -> Many2OneField<M8, M9>): Join10<M, M1, M2, M3, M4, M5, M6, M7, M8, M9> {
        return Join10(this, this.field.target.getter())
    }

    override fun <M1 : BaseModel<M1>> Many2OneField<M, M1>.predicate(getter: Alias<M1>.(M1) -> Predicate): Predicate {
        val join = Join2(this@SelectModelWhereBuilderImpl, this)
        joins.addIfNotIn(join)
        return join.alias.getter(this.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>> Join3<M, M1, M2>.predicate(getter: Alias<M2>.(M2) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>> Join4<M, M1, M2, M3>.predicate(getter: Alias<M3>.(M3) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>> Join5<M, M1, M2, M3, M4>.predicate(getter: Alias<M4>.(M4) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>> Join6<M, M1, M2, M3, M4, M5>.predicate(getter: Alias<M5>.(M5) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>> Join7<M, M1, M2, M3, M4, M5, M6>.predicate(getter: Alias<M6>.(M6) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>> Join8<M, M1, M2, M3, M4, M5, M6, M7>.predicate(getter: Alias<M7>.(M7) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>> Join9<M, M1, M2, M3, M4, M5, M6, M7, M8>.predicate(getter: Alias<M8>.(M8) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }

    override fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>, M9 : BaseModel<M9>> Join10<M, M1, M2, M3, M4, M5, M6, M7, M8, M9>.predicate(getter: Alias<M9>.(M9) -> Predicate): Predicate {
        joins.addIfNotIn(this)
        return this.alias.getter(this.field.target)
    }
}
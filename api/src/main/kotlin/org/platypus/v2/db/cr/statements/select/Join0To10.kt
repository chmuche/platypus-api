package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.db.SqlAble
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.Alias
import org.platypus.v2.model.AliasImpl
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.ReferenceField
import org.platypus.v2.utils.enter
import org.platypus.v2.utils.token


interface JoinStatementPart<M1 : BaseModel<M1>, M2 : BaseModel<M2>> : SqlAble {


    val field: ReferenceField<M1, M2>
    val alias: Alias<M2>
}

data class Join2<M1 : BaseModel<M1>, M2 : BaseModel<M2>>(
        val fromTable: Alias<M1>,
        override val field: ReferenceField<M1, M2>
) : JoinStatementPart<M1, M2> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(fromTable.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M2>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_2")
}

data class Join3<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>>(
        val join: Join2<M1, M2>,
        override val field: ReferenceField<M2, M3>
) : JoinStatementPart<M2, M3> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M3>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_3")
}

data class Join4<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>>(
        val join: Join3<M1, M2, M3>,
        override val field: ReferenceField<M3, M4>
) : JoinStatementPart<M3, M4> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M4>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_4")
}

data class Join5<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>>(
        val join: Join4<M1, M2, M3, M4>,
        override val field: ReferenceField<M4, M5>
) : JoinStatementPart<M4, M5> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M5>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_5")
}

data class Join6<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>>(
        val join: Join5<M1, M2, M3, M4, M5>,
        override val field: ReferenceField<M5, M6>
) : JoinStatementPart<M5, M6> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M6>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_6")
}

data class Join7<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>>(
        val join: Join6<M1, M2, M3, M4, M5, M6>,
        override val field: ReferenceField<M6, M7>
) : JoinStatementPart<M6, M7> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M7>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_7")
}

data class Join8<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>>(
        val join: Join7<M1, M2, M3, M4, M5, M6, M7>,
        override val field: ReferenceField<M7, M8>
) : JoinStatementPart<M7, M8> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M8>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_8")
}

data class Join9<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>, M9 : BaseModel<M9>>(
        val join: Join8<M1, M2, M3, M4, M5, M6, M7, M8>,
        override val field: ReferenceField<M8, M9>
) : JoinStatementPart<M8, M9> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M9>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_9")
}

data class Join10<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>, M9 : BaseModel<M9>, M10 : BaseModel<M10>>(
        val join: Join9<M1, M2, M3, M4, M5, M6, M7, M8, M9>,
        override val field: ReferenceField<M9, M10>
) : JoinStatementPart<M9, M10> {
    override fun toSql(dialect: DbDialect, builder: QueryBuilder): String = buildString {
        append(join.toSql(dialect, builder))
        enter()
        token("LEFT").token("JOIN")
        token(alias.tableName)
        token("ON")
        append(join.alias.alias).append(".").token(field.fieldName)
        token("=")
        append(alias.alias).append(".").token(field.target.id.fieldName)
    }

    override val alias: Alias<M10>
        get() = AliasImpl(this.field.target, "j_${this.field.hashCode()}_10")
}
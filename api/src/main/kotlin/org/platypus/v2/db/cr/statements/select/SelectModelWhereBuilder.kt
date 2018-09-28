package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.Alias
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.reference.Many2OneField

interface SelectModelWhereBuilder<M : BaseModel<M>> : FieldJoiner<M>, Alias<M> {


    infix fun <M1 : BaseModel<M1>> Many2OneField<M, M1>.predicate(getter: Alias<M1>.(M1) -> Predicate): Predicate

    infix fun <
            M1 : BaseModel<M1>,
            M2 : BaseModel<M2>>
            Join3<M, M1, M2>.predicate(getter: Alias<M2>.(M2) -> Predicate): Predicate


    infix fun <
            M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>>
            Join4<M, M1, M2, M3>.predicate(getter: Alias<M3>.(M3) -> Predicate): Predicate


    fun <M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>,
            M4 : BaseModel<M4>>
            Join5<M, M1, M2, M3, M4>.predicate(getter: Alias<M4>.(M4) -> Predicate): Predicate

    fun <M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>,
            M4 : BaseModel<M4>,
            M5 : BaseModel<M5>>
            Join6<M, M1, M2, M3, M4, M5>.predicate(getter: Alias<M5>.(M5) -> Predicate): Predicate

    fun <M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>,
            M4 : BaseModel<M4>,
            M5 : BaseModel<M5>,
            M6 : BaseModel<M6>>
            Join7<M, M1, M2, M3, M4, M5, M6>.predicate(getter: Alias<M6>.(M6) -> Predicate): Predicate

    fun <M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>,
            M4 : BaseModel<M4>,
            M5 : BaseModel<M5>,
            M6 : BaseModel<M6>,
            M7 : BaseModel<M7>>
            Join8<M, M1, M2, M3, M4, M5, M6, M7>.predicate(getter: Alias<M7>.(M7) -> Predicate): Predicate

    fun <M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>,
            M4 : BaseModel<M4>,
            M5 : BaseModel<M5>,
            M6 : BaseModel<M6>,
            M7 : BaseModel<M7>,
            M8 : BaseModel<M8>>
            Join9<M, M1, M2, M3, M4, M5, M6, M7, M8>.predicate(getter: Alias<M8>.(M8) -> Predicate): Predicate

    fun <M1 : BaseModel<M1>,
            M2 : BaseModel<M2>,
            M3 : BaseModel<M3>,
            M4 : BaseModel<M4>,
            M5 : BaseModel<M5>,
            M6 : BaseModel<M6>,
            M7 : BaseModel<M7>,
            M8 : BaseModel<M8>,
            M9 : BaseModel<M9>>
            Join10<M, M1, M2, M3, M4, M5, M6, M7, M8, M9>.predicate(getter: Alias<M9>.(M9) -> Predicate): Predicate

    fun FROM(dialect: DbDialect): String
    fun WHERE(dialect: DbDialect, predicate: Predicate): String
    fun ORDER_BY(dialect: DbDialect, predicate: Predicate): String
    fun LIMIT(dialect: DbDialect): String
    fun OFFSET(dbDialect: DbDialect): String
}
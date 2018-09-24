package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.reference.Many2OneField

interface SelectModelWhereBuilder<M : BaseModel<M>> : FieldJoiner<M> {


    infix fun <M1: BaseModel<M1>> Many2OneField<M, M1>.predicate(getter: M1.() -> Predicate): Predicate

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>>
            Join3<M, M1, M2>.predicate(getter: M2.() -> Predicate): Predicate


    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>>
            Join4<M, M1, M2, M3>.predicate(getter: M3.() -> Predicate): Predicate


    fun <M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>>
            Join5<M, M1, M2, M3, M4>.predicate(getter: M4.() -> Predicate): Predicate

    fun <M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>>
            Join6<M, M1, M2, M3, M4, M5>.predicate(getter: M5.() -> Predicate): Predicate

    fun <M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>>
            Join7<M, M1, M2, M3, M4, M5, M6>.predicate(getter: M6.() -> Predicate): Predicate

    fun <M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>,
            M7: BaseModel<M7>>
            Join8<M, M1, M2, M3, M4, M5, M6, M7>.predicate(getter: M7.() -> Predicate): Predicate

    fun <M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>,
            M7: BaseModel<M7>,
            M8: BaseModel<M8>>
            Join9<M, M1, M2, M3, M4, M5, M6, M7, M8>.predicate(getter: M8.() -> Predicate): Predicate

    fun <M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>,
            M7: BaseModel<M7>,
            M8: BaseModel<M8>,
            M9: BaseModel<M9>>
            Join10<M, M1, M2, M3, M4, M5, M6, M7, M8, M9>.predicate(getter: M9.() -> Predicate): Predicate
}
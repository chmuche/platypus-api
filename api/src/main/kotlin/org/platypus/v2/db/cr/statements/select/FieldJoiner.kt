package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.reference.Many2OneField

interface FieldJoiner<M: BaseModel<M>> {
    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>>
            Many2OneField<M, M1>.join(getter: M1.() -> Many2OneField<M1, M2>): Join3<M, M1, M2>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>>
            Join3<M, M1, M2>.join(getter: M2.() -> Many2OneField<M2, M3>): Join4<M, M1, M2, M3>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>>
            Join4<M, M1, M2, M3>.join(getter: M3.() -> Many2OneField<M3, M4>): Join5<M, M1, M2, M3, M4>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>>
            Join5<M, M1, M2, M3, M4>.join(getter: M4.() -> Many2OneField<M4, M5>): Join6<M, M1, M2, M3, M4, M5>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>>
            Join6<M, M1, M2, M3, M4, M5>.join(getter: M5.() -> Many2OneField<M5, M6>): Join7<M, M1, M2, M3, M4, M5, M6>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>,
            M7: BaseModel<M7>>
            Join7<M, M1, M2, M3, M4, M5, M6>.join(getter: M6.() -> Many2OneField<M6, M7>): Join8<M, M1, M2, M3, M4, M5, M6, M7>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>,
            M7: BaseModel<M7>,
            M8: BaseModel<M8>>
            Join8<M, M1, M2, M3, M4, M5, M6, M7>.join(getter: M7.() -> Many2OneField<M7, M8>): Join9<M, M1, M2, M3, M4, M5, M6, M7, M8>

    infix fun <
            M1: BaseModel<M1>,
            M2: BaseModel<M2>,
            M3: BaseModel<M3>,
            M4: BaseModel<M4>,
            M5: BaseModel<M5>,
            M6: BaseModel<M6>,
            M7: BaseModel<M7>,
            M8: BaseModel<M8>,
            M9: BaseModel<M9>>
            Join9<M, M1, M2, M3, M4, M5, M6, M7, M8>.join(getter: M8.() -> Many2OneField<M8, M9>): Join10<M, M1, M2, M3, M4, M5, M6, M7, M8, M9>
}
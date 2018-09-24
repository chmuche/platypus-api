package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.ReferenceField


interface JoinStatementPart<M1 : BaseModel<M1>, M2 : BaseModel<M2>> {


    val field: ReferenceField<M1, M2>
}

data class Join2<M1 : BaseModel<M1>, M2 : BaseModel<M2>>(
        val fromTable: M1,
        override val field: ReferenceField<M1, M2>
) : JoinStatementPart<M1, M2>

data class Join3<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>>(
        val join: Join2<M1, M2>,
        override val field: ReferenceField<M2, M3>
) : JoinStatementPart<M2, M3>

data class Join4<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>>(
        val join: Join3<M1, M2, M3>,
        override val field: ReferenceField<M3, M4>
) : JoinStatementPart<M3, M4>

data class Join5<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>>(
        val join: Join4<M1, M2, M3, M4>,
        override val field: ReferenceField<M4, M5>
) : JoinStatementPart<M4, M5>

data class Join6<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>>(
        val join: Join5<M1, M2, M3, M4, M5>,
        override val field: ReferenceField<M5, M6>
) : JoinStatementPart<M5, M6>

data class Join7<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>>(
        val join: Join6<M1, M2, M3, M4, M5, M6>,
        override val field: ReferenceField<M6, M7>
) : JoinStatementPart<M6, M7>

data class Join8<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>>(
        val join: Join7<M1, M2, M3, M4, M5, M6, M7>,
        override val field: ReferenceField<M7, M8>
) : JoinStatementPart<M7, M8>

data class Join9<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>, M9 : BaseModel<M9>>(
        val join: Join8<M1, M2, M3, M4, M5, M6, M7, M8>,
        override val field: ReferenceField<M8, M9>
) : JoinStatementPart<M8, M9>

data class Join10<M1 : BaseModel<M1>, M2 : BaseModel<M2>, M3 : BaseModel<M3>, M4 : BaseModel<M4>, M5 : BaseModel<M5>, M6 : BaseModel<M6>, M7 : BaseModel<M7>, M8 : BaseModel<M8>, M9 : BaseModel<M9>, M10 : BaseModel<M10>>(
        val join: Join9<M1, M2, M3, M4, M5, M6, M7, M8, M9>,
        override val field: ReferenceField<M9, M10>
) : JoinStatementPart<M9, M10>
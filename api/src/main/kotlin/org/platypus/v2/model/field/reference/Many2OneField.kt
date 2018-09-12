package org.platypus.v2.model.field.reference

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.FieldDDL
import org.platypus.v2.model.field.api.ReferenceField
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.visitor.BaseFieldVisitor

interface Many2OneField<M : BaseModel<M>, MT : BaseModel<MT>> : ReferenceField<M, MT> {

    val revertOne2Many: One2ManyField<MT, M>

    override fun createField(dbDialect: DbDialect): FieldDDL {
        return dbDialect.ddlUtil.createReferenceFieldDDL(this)
    }

    override fun <PARAM_TYPE, RETURN> accept(visitor: BaseFieldVisitor<PARAM_TYPE, RETURN>, p: PARAM_TYPE): RETURN = visitor.visit(this, p)

    interface Builder<M : BaseModel<M>, MT : BaseModel<MT>> : ReferenceField.Builder<M, MT, Many2OneField<M, MT>>
}
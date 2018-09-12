package org.platypus.v2.model.field.reference

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.LinkModel
import org.platypus.v2.model.ModelMany2Many
import org.platypus.v2.model.field.api.MultiReferencedField
import org.platypus.v2.visitor.BaseFieldVisitor

interface Many2ManyField<M : BaseModel<M>, MT : BaseModel<MT>> : MultiReferencedField<M, MT> {
    val link: ModelMany2Many.() -> LinkModel<M, MT>

    override fun <PARAM_TYPE, RETURN> accept(visitor: BaseFieldVisitor<PARAM_TYPE, RETURN>, p: PARAM_TYPE): RETURN = visitor.visit(this, p)

    interface Builder<M : BaseModel<M>, MT : BaseModel<MT>> : MultiReferencedField.Builder<M, MT, Many2ManyField<M, MT>> {
        val link: ModelMany2Many.() -> LinkModel<M, MT>
    }
}
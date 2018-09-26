package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.StringField

interface StringFieldPredicate<M : BaseModel<M>> : StringField<M>, BaseFieldPredicate<M, String>

class StringFieldAlias<M : BaseModel<M>>(val otherModel: BaseModel<M>, val original: StringField<M>) : StringField<M> by original, StringFieldPredicate<M>{
    override val model: M
        get() = otherModel as M


}
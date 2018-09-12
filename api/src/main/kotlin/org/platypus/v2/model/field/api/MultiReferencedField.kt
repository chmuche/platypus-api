package org.platypus.v2.model.field.api

import org.platypus.v2.model.BaseModel
import org.platypus.v2.record.bag.Bag

interface MultiReferencedField<M : BaseModel<M>, MT : BaseModel<MT>>: ClassicField<M, Bag<MT>> {
    val target: MT
    val domain: Boolean

    interface Builder<M : BaseModel<M>, MT : BaseModel<MT>, F : MultiReferencedField<M, MT>> : FieldBuilder<M, Bag<MT>, F> {
        val target: MT
        var domain: Boolean?
    }
}
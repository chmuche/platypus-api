package org.platypus.v2.model.field.api

import org.platypus.v2.model.BaseModel
import org.platypus.v2.db.ReferenceOption
import org.platypus.v2.record.one.Record

interface ReferenceField<M : BaseModel<M>, MT : BaseModel<MT>> : ClassicField<M, Record<MT>>, IndexableField {
    val target: MT
    val domain: Boolean
    val referenceOption: ReferenceOption
    val loadType: Boolean

    interface Builder<M : BaseModel<M>, MT : BaseModel<MT>, F : ReferenceField<M, MT>> : FieldBuilder<M, Record<MT>, F>, IndexableField.Mutable {
        val target: MT
        var domain: Boolean?
        var referenceOption: ReferenceOption?
        var loadType: Boolean?
    }
}
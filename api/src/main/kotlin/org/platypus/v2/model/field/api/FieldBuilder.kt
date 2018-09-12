package org.platypus.v2.model.field.api

import org.platypus.v2.model.BaseModel

interface FieldBuilder<M : BaseModel<M>, T : Any, F : BaseField<M, T>>: MutableFieldSlots<T> {

    fun build(): F
}
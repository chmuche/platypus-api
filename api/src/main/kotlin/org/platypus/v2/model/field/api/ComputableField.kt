package org.platypus.v2.model.field.api

import org.platypus.v2.model.BaseModel

interface ComputableField<M : BaseModel<M>, T : Any> {

//    val onGet: ComputeGetStacker<M, T>?
//    val onSet: ComputeSetStacker<M, T>?
}
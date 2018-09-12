package org.platypus.v2.model

interface FieldTransformer<T : Any> {

    fun transform(value: T): T
}

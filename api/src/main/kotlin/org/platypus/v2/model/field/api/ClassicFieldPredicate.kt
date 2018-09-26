package org.platypus.v2.model.field.api

import org.platypus.v2.db.predicate.EqPredicate
import org.platypus.v2.db.predicate.NEqPredicate
import org.platypus.v2.model.BaseModel

interface BaseFieldPredicate<M : BaseModel<M>, T : Any> : BaseField<M, T> {
    fun eq(value: T?) = EqPredicate(this, value)
    fun neq(value: T?) = NEqPredicate(this, value)
}
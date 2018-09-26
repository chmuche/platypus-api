package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.IntField

interface IntFieldPredicate<M : BaseModel<M>> : IntField<M>, BaseFieldPredicate<M, Int>

class IntFieldAlias<M : BaseModel<M>>(val original: IntField<M>) : IntField<M> by original, IntFieldPredicate<M>
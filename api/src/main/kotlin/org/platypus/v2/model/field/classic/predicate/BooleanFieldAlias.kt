package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.BooleanField

interface BooleanFieldPredicate<M : BaseModel<M>> : BooleanField<M>, BaseFieldPredicate<M, Boolean>

class BooleanFieldAlias<M : BaseModel<M>>(val original: BooleanField<M>) : BooleanField<M> by original, BooleanFieldPredicate<M>
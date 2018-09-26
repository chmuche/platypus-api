package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.BinaryField

interface BinaryFieldPredicate<M : BaseModel<M>> : BinaryField<M>, BaseFieldPredicate<M, ByteArray>

class BinaryFieldAlias<M : BaseModel<M>>(val original: BinaryField<M>) : BinaryField<M> by original, BinaryFieldPredicate<M>
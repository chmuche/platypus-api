package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.DecimalField
import java.math.BigDecimal

interface DecimalFieldPredicate<M : BaseModel<M>> : DecimalField<M>, BaseFieldPredicate<M, BigDecimal>

class DecimalFieldAlias<M : BaseModel<M>>(val original: DecimalField<M>) : DecimalField<M> by original, DecimalFieldPredicate<M>
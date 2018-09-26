package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.DateTimeField
import java.time.LocalDateTime

interface DateTimeFieldPredicate<M : BaseModel<M>> : DateTimeField<M>, BaseFieldPredicate<M, LocalDateTime>

class DateTimeFieldAlias<M : BaseModel<M>>(val original: DateTimeField<M>) : DateTimeField<M> by original, DateTimeFieldPredicate<M>
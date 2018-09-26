package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.DateField
import java.time.LocalDate

interface DateFieldPredicate<M : BaseModel<M>> : DateField<M>, BaseFieldPredicate<M, LocalDate>

class DateFieldAlias<M : BaseModel<M>>(val original: DateField<M>) : DateField<M> by original, DateFieldPredicate<M>
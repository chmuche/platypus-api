package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.Selection
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.SelectionField

interface SelectionFieldPredicate<M : BaseModel<M>, D : Selection<D>> : SelectionField<M, D>, BaseFieldPredicate<M, D>

class SelectionFieldAlias<M : BaseModel<M>, D : Selection<D>>(val original: SelectionField<M, D>) : SelectionField<M, D> by original, SelectionFieldPredicate<M, D>
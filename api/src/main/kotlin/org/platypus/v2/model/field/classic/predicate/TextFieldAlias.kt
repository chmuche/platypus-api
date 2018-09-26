package org.platypus.v2.model.field.classic.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseFieldPredicate
import org.platypus.v2.model.field.classic.TextField

interface TextFieldPredicate<M : BaseModel<M>> : TextField<M>, BaseFieldPredicate<M, String>

class TextFieldAlias<M : BaseModel<M>>(val original: TextField<M>) : TextField<M> by original, TextFieldPredicate<M>
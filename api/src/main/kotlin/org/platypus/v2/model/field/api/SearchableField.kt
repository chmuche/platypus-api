package org.platypus.v2.model.field.api

import org.platypus.v2.model.BaseModel

interface SearchableField<M : BaseModel<M>, T : Any> {
    val searchable: Boolean
}
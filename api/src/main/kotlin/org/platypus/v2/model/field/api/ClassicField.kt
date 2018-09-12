package org.platypus.v2.model.field.api

import org.platypus.v2.model.BaseModel
import org.platypus.v2.db.database.DbDialect

interface ClassicField<M : BaseModel<M>, T : Any> : BaseField<M, T> {

    override fun deleteField(dbDialect: DbDialect): FieldDDL { TODO("not implemented") }
}

interface MagicField<M : BaseModel<M>, T : Any> : BaseField<M, T>
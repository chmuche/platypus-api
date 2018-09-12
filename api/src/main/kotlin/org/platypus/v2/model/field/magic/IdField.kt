package org.platypus.v2.model.field.magic

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.FieldDDL
import org.platypus.v2.model.field.api.MagicField
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.utils.token
import org.platypus.v2.visitor.BaseFieldVisitor

interface IdField<M : BaseModel<M>> : MagicField<M, Int> {

    override fun createField(dbDialect: DbDialect): FieldDDL {
        return FieldDDL(buildString {
            token(dbDialect.identity(this@IdField))
            token("SERIAL")
            token("PRIMARY KEY")
        }, emptySet()
        )
    }

    override fun <PARAM_TYPE, RETURN> accept(visitor: BaseFieldVisitor<PARAM_TYPE, RETURN>, p: PARAM_TYPE): RETURN = visitor.visit(this, p)
}

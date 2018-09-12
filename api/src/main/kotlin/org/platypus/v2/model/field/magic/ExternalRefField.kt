package org.platypus.v2.model.field.magic

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.FieldDDL
import org.platypus.v2.model.field.api.MagicField
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.visitor.BaseFieldVisitor

interface ExternalRefField<M : BaseModel<M>> : MagicField<M, String>{

    override fun createField(dbDialect: DbDialect): FieldDDL {
        return dbDialect.ddlUtil.createClassicFieldDDL(this) {
            append("VARCHAR")
            append("(").append(30).append(")")
        }
    }

    override fun deleteField(dbDialect: DbDialect): FieldDDL {
        TODO("not implemented")
    }

    override fun <PARAM_TYPE, RETURN> accept(visitor: BaseFieldVisitor<PARAM_TYPE, RETURN>, p: PARAM_TYPE): RETURN = visitor.visit(this, p)
}


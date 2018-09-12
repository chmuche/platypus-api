package org.platypus.v2.model.field.magic

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.FieldDDL
import org.platypus.v2.model.field.api.MagicField
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.visitor.BaseFieldVisitor
import java.time.LocalDateTime

interface WriteDateField<M : BaseModel<M>> : MagicField<M, LocalDateTime>{

    override fun createField(dbDialect: DbDialect): FieldDDL {
        return dbDialect.ddlUtil.createClassicFieldDDL(this) { append("TIMESTAMP") }
    }

    override fun <PARAM_TYPE, RETURN> accept(visitor: BaseFieldVisitor<PARAM_TYPE, RETURN>, p: PARAM_TYPE): RETURN = visitor.visit(this, p)
}


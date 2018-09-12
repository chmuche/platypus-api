package org.platypus.v2.model.field.magic

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.UIWidget
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.FieldTransformer
import org.platypus.v2.db.OrmConstraint

class ExternalRefFieldImpl<M : BaseModel<M>>(override val model: M) : ExternalRefField<M> {
    override val usedModels: Pair<M, Set<BaseModel<*>>>  = model to emptySet()
    override val fieldName: String = "external_ref"
    override val label: String? = "The External Ref of this Record"
    override val copy: Boolean = false
    override val help: String? = null
    override val defaultValueFun: (env: PlatypusEnvironment) -> String? = { null }
    override val unique: Boolean = true
    override val groups: Set<String> = emptySet()
    override val widget: UIWidget? = null
    override val constraint: Set<OrmConstraint<String>> = emptySet()
    override val transformer: Set<FieldTransformer<String>> = emptySet()
    override val readonly: Boolean = true
    override val required: Boolean = false
    override val store: Boolean = true
}
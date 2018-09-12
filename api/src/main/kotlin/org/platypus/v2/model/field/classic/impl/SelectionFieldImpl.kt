package org.platypus.v2.model.field.classic.impl

import org.platypus.v2.db.OrmConstraint
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.UIWidget
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.FieldTransformer
import org.platypus.v2.model.field.Selection
import org.platypus.v2.model.field.classic.SelectionField

class SelectionFieldImpl<M : BaseModel<M>, D : Selection<D>>(
        override val fieldName: String,
        override val model: M,
        override val selection: D) : SelectionField<M, D> {
    override val usedModels: Pair<M, Set<BaseModel<*>>>  = model to emptySet()
    override var label: String? = null
        private set
    override var copy: Boolean = false
        private set
    override var index: Boolean = false
        private set
    override var help: String? = null
        private set
    override var defaultValueFun: ((env: PlatypusEnvironment) -> D?) = { null }
        private set
    override var unique: Boolean = false
        private set
    override var groups: Set<String> = emptySet()
        private set
    override var widget: UIWidget? = null
        private set
    override var constraint: Set<OrmConstraint<D>> = emptySet()
        private set
    override var transformer: Set<FieldTransformer<D>> = emptySet()
        private set
    override var readonly: Boolean = false
        private set
    override var required: Boolean = false
        private set
    override var store: Boolean = true
        private set

    class Builder<M : BaseModel<M>, D : Selection<D>>(
            val model: M,
            val fieldName: String,
            override val selection: D
    ) : SelectionField.Builder<M, D> {

        override var index: Boolean? = null
        override var label: String? = null
        override var help: String? = null
        override var copy: Boolean? = null
        override var defaultValueFun: ((env: PlatypusEnvironment) -> D)? = null
        override var unique: Boolean? = null
        override val groups: Set<String> = emptySet()
        override var widget: UIWidget? = null
        override var constraint: MutableSet<OrmConstraint<D>> = mutableSetOf()
        override var transformer: MutableSet<FieldTransformer<D>> = mutableSetOf()
        override var readonly: Boolean? = null
        override var required: Boolean? = null
        override var store: Boolean? = null

        override fun build(): SelectionField<M, D> {
            return SelectionFieldImpl(fieldName, model, selection).apply {
                index = this@Builder.index ?: index
                label = this@Builder.label ?: label
                copy = this@Builder.copy ?: copy
                help = this@Builder.help ?: help
                defaultValueFun = this@Builder.defaultValueFun ?: defaultValueFun
                unique = this@Builder.unique ?: unique
                groups += this@Builder.groups
                widget = this@Builder.widget ?: widget
                constraint += this@Builder.constraint
                transformer += this@Builder.transformer
                readonly = this@Builder.readonly ?: readonly
                required = this@Builder.required ?: required
                store = this@Builder.store ?: store
            }
        }
    }
}





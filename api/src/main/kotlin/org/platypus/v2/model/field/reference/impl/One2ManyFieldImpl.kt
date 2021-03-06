package org.platypus.v2.model.field.reference.impl

import org.platypus.v2.db.OrmConstraint
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.UIWidget
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.FieldTransformer
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.record.bag.Bag

class One2ManyFieldImpl<M : BaseModel<M>, MT : BaseModel<MT>>(
        override val fieldName: String,
        override val model: M,
        override val targetField: () -> Many2OneField<MT, M>) : One2ManyField<M, MT> {

    override val usedModels: Pair<M, Set<BaseModel<*>>>
        get() = model to setOf(target)

    override val target: MT
        get() = targetField().model
    override var domain: Boolean = false
        private set
    override var label: String? = null
        private set
    override var copy: Boolean = false
        private set
    override var help: String? = null
        private set
    override var defaultValueFun: ((env: PlatypusEnvironment) -> Bag<MT>?) = { null }
        private set
    override var unique: Boolean = false
        private set
    override var groups: Set<String> = emptySet()
        private set
    override var widget: UIWidget? = null
        private set
    override var constraint: Set<OrmConstraint<Bag<MT>>> = emptySet()
        private set
    override var transformer: Set<FieldTransformer<Bag<MT>>> = emptySet()
        private set
    override var readonly: Boolean = false
        private set
    override var required: Boolean = false
        private set
    override val store: Boolean = false

    class Builder<M : BaseModel<M>, MT : BaseModel<MT>>(
            val model: M,
            val fieldName: String,
            override val targetField: () -> Many2OneField<MT, M>) : One2ManyField.Builder<M, MT> {

        override val target: MT
            get() = targetField().model

        override var domain: Boolean? = null
        override var label: String? = null
        override var help: String? = null
        override var copy: Boolean? = null
        override var defaultValueFun: ((env: PlatypusEnvironment) -> Bag<MT>)? = null
        override var unique: Boolean? = null
        override val groups: Set<String> = emptySet()
        override var widget: UIWidget? = null
        override var constraint: MutableSet<OrmConstraint<Bag<MT>>> = mutableSetOf()
        override var transformer: MutableSet<FieldTransformer<Bag<MT>>> = mutableSetOf()
        override var readonly: Boolean? = null
        override var required: Boolean? = null
        override var store: Boolean?
            get() = TODO("not implemented")
            set(value) {
                TODO("not implemented")
            }

        override fun build(): One2ManyField<M, MT> {
            return One2ManyFieldImpl(fieldName, model, targetField).apply {
                domain = this@Builder.domain ?: domain
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
            }
        }
    }
}





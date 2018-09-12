package org.platypus.v2.model.field.classic.impl

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.UIWidget
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.FieldTransformer
import org.platypus.v2.model.field.classic.StringField
import org.platypus.v2.db.OrmConstraint

class StringFieldImpl<M : BaseModel<M>>(
        override val fieldName: String,
        override val model: M) : StringField<M> {

    override val usedModels: Pair<M, Set<BaseModel<*>>>  = model to emptySet()
    override var length: Int = 255
        private set
    override var label: String? = null
        private set
    override var copy: Boolean = false
        private set
    override var index: Boolean = false
        private set
    override var help: String? = null
        private set
    override var defaultValueFun: ((env: PlatypusEnvironment) -> String?) = { null }
        private set
    override var unique: Boolean = false
        private set
    override var groups: Set<String> = emptySet()
        private set
    override var widget: UIWidget? = null
        private set
    override var constraint: Set<OrmConstraint<String>> = emptySet()
        private set
    override var transformer: Set<FieldTransformer<String>> = emptySet()
        private set
    override var readonly: Boolean = false
        private set
    override var required: Boolean = false
        private set
    override var store: Boolean = true
        private set

    class Builder<M : BaseModel<M>> constructor(val model: M, val fieldName: String) : StringField.Builder<M> {

        override var length: Int? = null
        override var index: Boolean? = null
        override var label: String? = null
        override var help: String? = null
        override var copy: Boolean? = null
        override var defaultValueFun: ((env: PlatypusEnvironment) -> String)? = null
        override var unique: Boolean? = null
        override val groups: Set<String> = emptySet()
        override var widget: UIWidget? = null
        override var constraint: MutableSet<OrmConstraint<String>> = mutableSetOf()
        override var transformer: MutableSet<FieldTransformer<String>> = mutableSetOf()
        override var readonly: Boolean? = null
        override var required: Boolean? = null
        override var store: Boolean? = null

        override fun build(): StringField<M> {
            return StringFieldImpl(fieldName, model).apply {
                length = this@Builder.length ?: length
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





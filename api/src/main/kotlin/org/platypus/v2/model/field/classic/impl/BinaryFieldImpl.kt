package org.platypus.v2.model.field.classic.impl

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.UIWidget
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.FieldTransformer
import org.platypus.v2.model.field.classic.BinaryField
import org.platypus.v2.db.OrmConstraint

class BinaryFieldImpl<M : BaseModel<M>>(
        override val fieldName: String,
        override val model: M) : BinaryField<M> {

    override val usedModels: Pair<M, Set<BaseModel<*>>>  = model to emptySet()

    override var attachment: Boolean = false
        private set
    override var label: String? = null
        private set
    override var copy: Boolean = false
        private set
    override var help: String? = null
        private set
    override var defaultValueFun: ((env: PlatypusEnvironment) -> ByteArray?) = { null }
        private set
    override var unique: Boolean = false
        private set
    override var groups: Set<String> = emptySet()
        private set
    override var widget: UIWidget? = null
        private set
    override var constraint: Set<OrmConstraint<ByteArray>> = emptySet()
        private set
    override var transformer: Set<FieldTransformer<ByteArray>> = emptySet()
        private set
    override var readonly: Boolean = false
        private set
    override var required: Boolean = false
        private set
    override var store: Boolean = true
        private set

    class Builder<M : BaseModel<M>> constructor(val model: M, val fieldName: String) : BinaryField.Builder<M> {

        override var label: String? = null
        override var help: String? = null
        override var copy: Boolean? = null
        override var defaultValueFun: ((env: PlatypusEnvironment) -> ByteArray)? = null
        override var unique: Boolean? = null
        override val groups: Set<String> = emptySet()
        override var widget: UIWidget? = null
        override var constraint: MutableSet<OrmConstraint<ByteArray>> = mutableSetOf()
        override var transformer: MutableSet<FieldTransformer<ByteArray>> = mutableSetOf()
        override var readonly: Boolean? = null
        override var required: Boolean? = null
        override var store: Boolean? = null
        override var attachment: Boolean? = null

        override fun build(): BinaryField<M> {
            return BinaryFieldImpl(fieldName, model).apply {
                attachment = this@Builder.attachment ?: attachment
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





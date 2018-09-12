package org.platypus.v2.model

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.field.Selection
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.api.FieldBuilder
import org.platypus.v2.model.field.classic.BinaryField
import org.platypus.v2.model.field.classic.BooleanField
import org.platypus.v2.model.field.classic.DateField
import org.platypus.v2.model.field.classic.DateTimeField
import org.platypus.v2.model.field.classic.DecimalField
import org.platypus.v2.model.field.classic.IntField
import org.platypus.v2.model.field.classic.SelectionField
import org.platypus.v2.model.field.classic.StringField
import org.platypus.v2.model.field.classic.TextField
import org.platypus.v2.model.field.classic.TimeField
import org.platypus.v2.model.field.classic.impl.BinaryFieldImpl
import org.platypus.v2.model.field.classic.impl.BooleanFieldImpl
import org.platypus.v2.model.field.classic.impl.DateFieldImpl
import org.platypus.v2.model.field.classic.impl.DateTimeFieldImpl
import org.platypus.v2.model.field.classic.impl.DecimalFieldImpl
import org.platypus.v2.model.field.classic.impl.IntFieldImpl
import org.platypus.v2.model.field.classic.impl.SelectionFieldImpl
import org.platypus.v2.model.field.classic.impl.StringFieldImpl
import org.platypus.v2.model.field.classic.impl.TextFieldImpl
import org.platypus.v2.model.field.classic.impl.TimeFieldImpl
import org.platypus.v2.model.field.magic.IdField
import org.platypus.v2.model.field.magic.IdFieldImpl
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.model.field.reference.impl.Many2ManyFieldImpl
import org.platypus.v2.model.field.reference.impl.Many2OneFieldImpl
import org.platypus.v2.model.field.reference.impl.One2ManyFieldImpl
import org.platypus.v2.utils.appendIf
import org.platypus.v2.utils.comma
import org.platypus.v2.utils.space
import java.util.*

abstract class Model<M : Model<M>>(override val modelName: String) : BaseModel<M> {


    private val thisModel: M
        get() = this as M

    override val id: IdField<M> = IdFieldImpl(thisModel)

    override val fields: Set<BaseField<M, *>>
        get() = internalFields

    private val internalFields = HashSet<BaseField<M, *>>()

    override fun load() {
        super.load()
        internalFields.add(id)
    }

    protected fun string(name: String, info: StringField.Builder<M>.() -> Unit = {}): StringField<M> =
            StringFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun buildInName(info: StringField.Builder<M>.() -> Unit = {}): StringField<M> =
            StringFieldImpl.Builder(thisModel, "name").registerField(info)

    protected fun date(name: String, info: DateField.Builder<M>.() -> Unit = {}): DateField<M> =
            DateFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun dateTime(name: String, info: DateTimeField.Builder<M>.() -> Unit = {}): DateTimeField<M> =
            DateTimeFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun time(name: String, info: TimeField.Builder<M>.() -> Unit = {}): TimeField<M> =
            TimeFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun boolean(name: String, info: BooleanField.Builder<M>.() -> Unit = {}): BooleanField<M> =
            BooleanFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun text(name: String, info: TextField.Builder<M>.() -> Unit = {}): TextField<M> =
            TextFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun decimal(name: String, info: DecimalField.Builder<M>.() -> Unit = {}): DecimalField<M> =
            DecimalFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun integer(name: String, info: IntField.Builder<M>.() -> Unit = {}): IntField<M> =
            IntFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun binary(name: String, info: BinaryField.Builder<M>.() -> Unit = {}): BinaryField<M> =
            BinaryFieldImpl.Builder(thisModel, name).registerField(info)

    protected fun <TM : Model<TM>> many2many(name: String, target: ModelMany2Many.() -> LinkModel<M, TM>, info: Many2ManyField.Builder<M, TM>.() -> Unit = {}): Many2ManyField<M, TM> {
        return Many2ManyFieldImpl.Builder(thisModel, name, target).registerField(info)
    }

    protected fun <TM : Model<TM>> many2manyR(name: String, target: ModelMany2Many.() -> LinkModel<TM, M>, info: Many2ManyField.Builder<M, TM>.() -> Unit = {}): Many2ManyField<M, TM> {
        return Many2ManyFieldImpl.Builder(thisModel, name, { target(ModelMany2Many).reverse }).registerField(info)
    }

    protected fun <D : Selection<D>> selection(name: String, selection: D, info: SelectionField.Builder<M, D>.() -> Unit = {}): SelectionField<M, D> =
            SelectionFieldImpl.Builder(thisModel, name, selection).registerField(info)

    /**
     * Create a Reverse link between to entity.
     * This field don't really exist in the database.
     * @param name The name of the field
     * @param targetField A lambda with the targeted [Many2OneField]
     * @param info The builder to fieldSet additional info
     */
    protected fun <TM : Model<TM>> one2many(name: String, targetField: () -> Many2OneField<TM, M>,
                                            info: One2ManyField.Builder<M, TM>.() -> Unit = {}): One2ManyField<M, TM> =
            One2ManyFieldImpl.Builder(thisModel, name, targetField).registerField(info)

    /**
     * Create a real link between to [StoredEntity]
     * @param name The name of the field/column in the database
     * @param target the Target  entity to use to create the link.
     * The field id is used to create de link
     * @param info The builder to fieldSet additionnal info
     */
    protected fun <TM : Model<TM>> many2one(name: String, target: TM, info: Many2OneField.Builder<M, TM>.() -> Unit = {}): Many2OneField<M, TM> =
            Many2OneFieldImpl.Builder(thisModel, name, target).registerField(info)


    private fun <B : FieldBuilder<M, T, FIELD>, FIELD : BaseField<M, T>, T : Any>
            B.registerField(info: B.() -> Unit): FIELD {
        this.info()
        val f = this.build()
        internalFields.add(f)
        return f
    }

    override fun createBaseModel(dbDialect: DbDialect): ModelDDL {
        val builder = StringBuilder("CREATE TABLE")
        builder.appendIf(dbDialect.supportsIfNotExists, " IF NOT EXISTS ")
        builder.append(dbDialect.identity(this))
        val fIterator = fields.filter { it.store }.iterator()
        val constraint = HashSet<String>()
        if (fIterator.hasNext()) {
            builder.space().append("(")
            while (fIterator.hasNext()) {
                val field = fIterator.next()
                val fieldDDl = field.createField(dbDialect)
                constraint.addAll(fieldDDl.constraint)
                if (fieldDDl.fieldDef.isNotBlank()) {
                    builder.appendIf(true, "\n\t")
                    builder.append(fieldDDl.fieldDef)
                    if (fIterator.hasNext()) {
                        builder.comma()
                    }
                }

            }
            builder.appendIf(true, "\n").append(")")
        }
        return ModelDDL(builder.toString(), constraint)
    }
}

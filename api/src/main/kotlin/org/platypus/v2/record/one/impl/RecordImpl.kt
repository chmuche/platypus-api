package org.platypus.v2.record.one.impl

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.Selection
import org.platypus.v2.model.field.SelectionValue
import org.platypus.v2.model.field.api.BaseField
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
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.bag.BagRecordImpl
import org.platypus.v2.record.one.ImmutableRecordField
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.security.PlatypusUser
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KProperty

class RecordImpl<M : BaseModel<M>>(
        override val env: PlatypusEnvironment,
        override val id: Int,
        val model: M
) : Record<M> {


    override fun toBag(): Bag<M> {
        return BagRecordImpl(env, model, listOf(id))
    }

    override fun plus(other: Record<M>): Bag<M> {
        TODO("not implemented")
    }

    override fun validate(): Set<String> {
        TODO("not implemented")
    }

    override fun isStore(): Boolean {
        TODO("not implemented")
    }

    override fun fetch(): Record<M> {
        TODO("not implemented")
    }

    override fun fetchIfNeeded(field: BaseField<M, *>): Record<M> {
        TODO("not implemented")
    }

    override fun copy(): Record<M> {
        TODO("not implemented")
    }

    override fun deepCopy(): Record<M> {
        TODO("not implemented")
    }

    override fun delete() {
        TODO("not implemented")
    }

    override fun update(builder: RecordBuilder<M>) {
        TODO("not implemented")
    }

    override fun withContext(vararg vals: Pair<String, Any>): Record<M> {
        TODO("not implemented")
    }

    override fun sudo(user: PlatypusUser): Record<M> {
        TODO("not implemented")
    }

    override fun TimeField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalTime? {
        TODO("not implemented")
    }

    override fun StringField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): String? {
        TODO("not implemented")
    }

    override fun TextField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): String? {
        TODO("not implemented")
    }

    override fun DateField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): LocalDate? {
        TODO("not implemented")
    }

    override fun DateTimeField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): LocalDateTime? {
        TODO("not implemented")
    }

    override fun BooleanField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Boolean {
        TODO("not implemented")
    }

    override fun DecimalField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): BigDecimal {
        TODO("not implemented")
    }

    override fun IntField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Int {
        TODO("not implemented")
    }

    override fun <D : Selection<D>> SelectionField<M, D>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): SelectionValue<D>? {
        TODO("not implemented")
    }

    override fun BinaryField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): ByteArray? {
        TODO("not implemented")
    }

    override fun <TM : BaseModel<TM>> One2ManyField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Bag<TM> {
        TODO("not implemented")
    }

    override fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Bag<TM> {
        TODO("not implemented")
    }

    override fun <TM : BaseModel<TM>> Many2OneField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Record<TM> {
        TODO("not implemented")
    }

    override val displayName: String
        get() = TODO("not implemented")
    override val createDate: LocalDateTime
        get() = TODO("not implemented")
    override val writeDate: LocalDateTime
        get() = TODO("not implemented")
    override val createUid: PlatypusUser
        get() = TODO("not implemented")
    override val writeUid: PlatypusUser
        get() = TODO("not implemented")
    override val externalRef: String?
        get() = TODO("not implemented")
}
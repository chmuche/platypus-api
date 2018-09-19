package org.platypus.v2.record.one.impl

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
import org.platypus.v2.record.one.BagBuilder
import org.platypus.v2.record.one.BagBuilderImpl
import org.platypus.v2.record.one.BagBuilderToStore
import org.platypus.v2.record.one.MutableRecordBuilder
import org.platypus.v2.record.one.MutableRecordBuilderToStore
import org.platypus.v2.record.one.PersistedRecord
import org.platypus.v2.record.one.PersistedRecordImp
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.one.RecordBuilderToStore
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KProperty

class RecordBuilderToStoreImpl<M : BaseModel<M>>(initData: Map<BaseField<M, *>, Any?>) : MutableRecordBuilderToStore<M> {
    private val data = HashMap<BaseField<M, *>, Any?>(initData)
    override val rawData: Map<BaseField<M, *>, Any?>
        get() = data

    override fun change(mutateBlock: MutableRecordBuilderToStore<M>.() -> Unit): RecordBuilderToStore<M> {
        val toMutate = RecordBuilderToStoreImpl(rawData)
        toMutate.mutateBlock()
        return toMutate
    }

    fun <T : Any> BaseField<M, T>.getValue(default: T): T = data.getOrDefault(this, default) as T

    override fun IntField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: Int?) {
        data[this] = value
    }

    override fun StringField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: String?) {
        data[this] = value
    }

    override fun <D : Selection<D>> SelectionField<M, D>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: SelectionValue<D>?) {
        data[this] = value
    }

    override fun TextField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: String?) {
        data[this] = value
    }

    override fun DateField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: LocalDate?) {
        data[this] = value
    }

    override fun DateTimeField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: LocalDateTime?) {
        data[this] = value
    }

    override fun TimeField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: LocalTime?) {
        data[this] = value
    }

    override fun BooleanField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: Boolean?) {
        data[this] = value
    }

    override fun DecimalField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: BigDecimal?) {
        data[this] = value
    }

    override fun BinaryField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: ByteArray?) {
        data[this] = value
    }

    override fun TimeField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): LocalTime? {
        return data[this] as LocalTime?
    }

    override fun StringField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): String? {
        return data[this] as String?
    }

    override fun TextField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): String? {
        return data[this] as String?
    }

    override fun DateField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): LocalDate? {
        return data[this] as LocalDate?
    }

    override fun DateTimeField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): LocalDateTime? {
        return data[this] as LocalDateTime?
    }

    override fun BooleanField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): Boolean {
        return data[this] as Boolean? ?: false
    }

    override fun DecimalField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): BigDecimal {
        return getValue(BigDecimal.ZERO)
    }


    override fun IntField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): Int {
        return getValue(0)
    }

    override fun <D : Selection<D>> SelectionField<M, D>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): SelectionValue<D>? {
        return data[this] as SelectionValue<D>?
    }

    override fun BinaryField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): ByteArray? {
        return data[this] as ByteArray?
    }

    override fun <TM : BaseModel<TM>> One2ManyField<M, TM>.getValue(o: RecordBuilderToStore<M>, desc: KProperty<*>): BagBuilderToStore<M, TM> {
        return BagBuilderImpl(this)
    }

    override fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.getValue(o: RecordBuilderToStore<M>, desc: KProperty<*>): BagBuilderToStore<M, TM> {
        return BagBuilderImpl(this)
    }

    override fun <TM : BaseModel<TM>> Many2OneField<M, TM>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): PersistedRecord<TM>? {
        return (data[this] as Int?)?.let { PersistedRecordImp(it, this.target) }
    }

    override fun <TM : BaseModel<TM>> Many2OneField<M, TM>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: PersistedRecord<M>) {
        data[this] = value
    }

    override fun <TM : BaseModel<TM>> BagBuilder<M, TM>.add(records: Bag<TM>) {
        val actualIds = data.getOrDefault(this.field, HashSet<Int>())  as MutableSet<Int>
        actualIds.addAll(records.ids)
        data[this.field] = actualIds
    }

    override fun <TM : BaseModel<TM>> BagBuilder<M, TM>.add(record: PersistedRecord<TM>) {
        val actualIds = data.getOrDefault(this.field, HashSet<Int>())  as MutableSet<Int>
        actualIds.add(record.id)
        data[this.field] = actualIds
    }

    override fun <TM : BaseModel<TM>> BagBuilder<M, TM>.removeAll() {
        data[this.field] = HashSet<Int>()
    }
}
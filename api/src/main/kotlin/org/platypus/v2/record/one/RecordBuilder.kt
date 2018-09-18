package org.platypus.v2.record.one

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
import org.platypus.v2.utils.Environmentable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KProperty

interface RecordBuilder<M : BaseModel<M>> : Environmentable {
    val rawData: Map<BaseField<M, *>, Any?>

    operator fun TimeField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): LocalTime?
    operator fun StringField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): String?
    operator fun TextField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): String?
    operator fun DateField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): LocalDate?
    operator fun DateTimeField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): LocalDateTime?
    operator fun BooleanField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): Boolean
    operator fun DecimalField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): BigDecimal
    operator fun IntField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): Int
    operator fun <D : Selection<D>> SelectionField<M, D>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): SelectionValue<D>?
    operator fun BinaryField<M>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): ByteArray?
    operator fun <TM : BaseModel<TM>> One2ManyField<M, TM>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): BagBuilder<M, TM>
    operator fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): BagBuilder<M, TM>
    operator fun <TM : BaseModel<TM>> Many2OneField<M, TM>.getValue(o: RecordBuilder<M>, desc: KProperty<*>): Record<TM>

    operator fun IntField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: Int?)
    operator fun StringField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: String?)
    operator fun <D : Selection<D>> SelectionField<M, D>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: SelectionValue<D>?)
    operator fun TextField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: String?)
    operator fun DateField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: LocalDate?)
    operator fun DateTimeField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: LocalDateTime?)
    operator fun TimeField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: LocalTime?)
    operator fun BooleanField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: Boolean?)
    operator fun DecimalField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: BigDecimal?)
    operator fun BinaryField<M>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: ByteArray?)
    operator fun <TM : BaseModel<TM>> Many2OneField<M, TM>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: Record<TM>?)
    operator fun <TM : BaseModel<TM>> One2ManyField<M, TM>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: BagBuilder<M, TM>)
    operator fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.setValue(o: RecordBuilder<M>, desc: KProperty<*>, value: BagBuilder<M, TM>)

    operator fun <TM : BaseModel<TM>> BagBuilder<M, TM>.plusAssign(rec:Record<TM>)
    operator fun <TM : BaseModel<TM>> BagBuilder<M, TM>.plusAssign(rec: Bag<TM>)

    operator fun <TM : BaseModel<TM>> BagBuilder<M, TM>.minusAssign(rec:Record<TM>)
    operator fun <TM : BaseModel<TM>> BagBuilder<M, TM>.minusAssign(rec: Bag<TM>)
}

interface BagBuilder<M:BaseModel<M>, TM:BaseModel<TM>>{

    fun replaceWith(records:Bag<TM>)
    fun replaceWith(record:Record<TM>)

    fun onlyKeep(records:Bag<TM>)
    fun onlyKeep(record:Record<TM>)

    fun remove(records:Bag<TM>)
    fun remove(record:Record<TM>)

    fun add(records:Bag<TM>)
    fun add(record:Record<TM>)

    fun removeAll()

}
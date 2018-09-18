package org.platypus.v2.record.one

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.Selection
import org.platypus.v2.model.field.SelectionValue
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
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KProperty

interface RecordField<M : BaseModel<M>> : ImmutableRecordField<M> {

    operator fun IntField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: Int?)
    operator fun StringField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: String?)
    operator fun <D : Selection<D>> SelectionField<M, D>.setValue(o: RecordField<M>, desc: KProperty<*>, value: SelectionValue<D>?)
    operator fun TextField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: String?)
    operator fun DateField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: LocalDate?)
    operator fun DateTimeField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: LocalDateTime?)
    operator fun TimeField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: LocalTime?)
    operator fun BooleanField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: Boolean?)
    operator fun DecimalField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: BigDecimal?)
    operator fun BinaryField<M>.setValue(o: RecordField<M>, desc: KProperty<*>, value: ByteArray?)
    operator fun <TM : BaseModel<TM>> Many2OneField<M, TM>.setValue(o: RecordField<M>, desc: KProperty<*>, value: Record<TM>?)
    operator fun <TM : BaseModel<TM>> One2ManyField<M, TM>.setValue(o: RecordField<M>, desc: KProperty<*>, value: Bag<TM>)
    operator fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.setValue(o: RecordField<M>, desc: KProperty<*>, value: Bag<TM>)


}
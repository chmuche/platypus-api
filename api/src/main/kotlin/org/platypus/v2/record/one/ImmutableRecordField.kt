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
import org.platypus.v2.model.field.magic.CreateDateField
import org.platypus.v2.model.field.magic.CreateUserField
import org.platypus.v2.model.field.magic.ExternalRefField
import org.platypus.v2.model.field.magic.WriteDateField
import org.platypus.v2.model.field.magic.WriteUserField
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.Many2OneField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.security.PlatypusUser
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KProperty

interface ImmutableRecordField<M : BaseModel<M>> {


    operator fun TimeField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalTime?
    operator fun StringField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): String?
    operator fun TextField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): String?
    operator fun DateField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): LocalDate?
    operator fun DateTimeField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): LocalDateTime?
    operator fun BooleanField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Boolean
    operator fun DecimalField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): BigDecimal
    operator fun IntField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Int
    operator fun <D : Selection<D>> SelectionField<M, D>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): SelectionValue<D>?
    operator fun BinaryField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): ByteArray?
    operator fun <TM : BaseModel<TM>> One2ManyField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Bag<TM>
    operator fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Bag<TM>
    operator fun <TM : BaseModel<TM>> Many2OneField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Record<TM>
}
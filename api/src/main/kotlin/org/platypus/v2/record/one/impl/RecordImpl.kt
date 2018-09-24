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
        override val model: M
) : Record<M> {


    override fun toBag(): Bag<M> {
        return BagRecordImpl(env, model, listOf(id))
    }

    override fun plus(other: Record<M>): Bag<M> {
        return BagRecordImpl(env, model, listOf(id, other.id))
    }

    override fun validate(): Set<String> {
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

    override fun TimeField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalTime? = env.cr.cache[model to id][this] as LocalTime?


    override fun StringField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): String? = env.cr.cache[model to id][this] as String?

    override fun TextField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): String? = env.cr.cache[model to id][this] as String?

    override fun DateField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): LocalDate? = env.cr.cache[model to id][this] as LocalDate?

    override fun DateTimeField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): LocalDateTime? = env.cr.cache[model to id][this] as LocalDateTime?

    override fun BooleanField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Boolean = env.cr.cache[model to id][this] as Boolean?
            ?: false

    override fun DecimalField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): BigDecimal = env.cr.cache[model to id][this] as BigDecimal?
            ?: BigDecimal.ZERO

    override fun IntField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Int = env.cr.cache[model to id][this] as Int?
            ?: 0

    override fun <D : Selection<D>> SelectionField<M, D>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): SelectionValue<D>? = env.cr.cache[model to id][this] as SelectionValue<D>?

    override fun BinaryField<M>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): ByteArray? = env.cr.cache[model to id][this] as ByteArray?

    override fun <TM : BaseModel<TM>> One2ManyField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Bag<TM> {
        val targetIds = env.cr.cache[model to id][this] as List<Int> ?: emptyList()
        return BagRecordImpl(env, this.target, targetIds)

    }

    override fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Bag<TM> {
        val targetIds = env.cr.cache[model to id][this] as List<Int> ?: emptyList()
        return BagRecordImpl(env, this.target, targetIds)
    }

    override fun <TM : BaseModel<TM>> Many2OneField<M, TM>.getValue(o: ImmutableRecordField<M>, desc: KProperty<*>): Record<TM> {
        val targetId = env.cr.cache[model to id][this] as Int? ?: -1
        return RecordImpl(env, targetId, this.target)
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
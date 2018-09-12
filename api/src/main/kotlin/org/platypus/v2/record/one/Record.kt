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
import org.platypus.v2.utils.ContextAble
import org.platypus.v2.utils.Environmentable
import org.platypus.v2.utils.Identifiable
import org.platypus.v2.utils.SudoAble
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.reflect.KProperty

interface Record<M : BaseModel<M>> : SudoAble<Record<M>>, ContextAble<Record<M>>, Environmentable, Identifiable {

    /**
     * The string representation of the current Record
     */
    val displayName: String
    /**
     * The creation date of this record, the [LocalDateTime.now] is used when this record is created
     */
    val createDate: LocalDateTime
    /**
     * The last write date of this record, the [LocalDateTime.now] is used when this record is created
     */
    val writeDate: LocalDateTime
    /**
     * The [ReadOnlyPlatypusEnvironment.envUser] who create this record
     */
    val createUid: PlatypusUser
    /**
     * The [ReadOnlyPlatypusEnvironment.envUser] who update the last time this record
     */
    val writeUid: PlatypusUser
    /**
     * The external refeterence to used in the method [Repository.byRef]
     */
    val externalRef: String?

    /**
     * Convert this Record to a bag with only one element witch is the current record
     * The implementation should guarantee that code is correct [this.toBag().first() === this]
     */
    fun toBag(): Bag<M>

    /**
     * An handly method to add the current Record to a [bag] with an other record of the same [Model]
     */
    operator fun plus(other: Record<M>): Bag<M>

    /**
     * Validate the entity and return a [Set] of [String] if some error exist
     * No [Exception] will fire
     *
     * The [Set] can be empty if no error is detected
     * See the current Implementation to see what is validated in the current record
     */
    fun validate(): Set<String>

    /**
     * Return [true] if the current record is in persitence layer or will be at the end of the [PlatypusEnvironement]
     */
    fun isStore(): Boolean

    /**
     * Fetch the current entity with the current [predicate] and erase the value in the cache with the new one
     * Before a flush only for this record is processed
     * Can throw a [MissingRecordException] if the entity don't exist anymore in the persitence layer
     */
    fun fetch(): Record<M>

    /**
     * Execute a fetch if the current field is not in the [PlatypusCache] retreive by [warmCache]
     *
     * If the field is in cache the nothing append,
     * overrise fetch the current field and all the other field withch is not in cache for the current [Record] only
     */
    fun fetchIfNeeded(field: BaseField<M, *>): Record<M>

    /**
     * Create a copy of it self
     * The copy is always in the state [EntityState.MANAGED]
     *
     * By default the copy only copey the field with the [FieldSlot.copy] == true
     * The relation of the current record will no be copy
     */
    fun copy(): Record<M>


    /**
     * Same as copy but copy the linked object too
     * @see copy
     */
    fun deepCopy(): Record<M>

    /**
     * Remove the entity of the database
     * put the [internalState] to [EntityState.DELETED]
     */
    fun delete()

    operator fun set(fieldName: String, value: Any?)

    //region GETTER
    operator fun TimeField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalTime?

    operator fun ExternalRefField<M>.getValue(o: Record<M>, desc: KProperty<*>): String?
    operator fun StringField<M>.getValue(o: Record<M>, desc: KProperty<*>): String?
    operator fun TextField<M>.getValue(o: Record<M>, desc: KProperty<*>): String?
    operator fun DateField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalDate?
    operator fun DateTimeField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalDateTime?
    operator fun BooleanField<M>.getValue(o: Record<M>, desc: KProperty<*>): Boolean
    operator fun DecimalField<M>.getValue(o: Record<M>, desc: KProperty<*>): BigDecimal
    operator fun IntField<M>.getValue(o: Record<M>, desc: KProperty<*>): Int
    operator fun <D : Selection<D>> SelectionField<M, D>.getValue(o: Record<M>, desc: KProperty<*>): SelectionValue<D>?
    operator fun BinaryField<M>.getValue(o: Record<M>, desc: KProperty<*>): ByteArray?
    operator fun CreateUserField<M>.getValue(o: Record<M>, desc: KProperty<*>): PlatypusUser
    operator fun WriteUserField<M>.getValue(o: Record<M>, desc: KProperty<*>): PlatypusUser
    operator fun <TM : BaseModel<TM>> One2ManyField<M, TM>.getValue(o: Record<M>, desc: KProperty<*>): Bag<TM>
    operator fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.getValue(o: Record<M>, desc: KProperty<*>): Bag<TM>
    operator fun <TM : BaseModel<TM>> Many2OneField<M, TM>.getValue(o: Record<M>, desc: KProperty<*>): Record<TM>
    operator fun CreateDateField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalDateTime
    operator fun WriteDateField<M>.getValue(o: Record<M>, desc: KProperty<*>): LocalDateTime?
    //endregion

    //region SETTER
    operator fun IntField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: Int?)

    operator fun StringField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: String?)
    operator fun <D : Selection<D>> SelectionField<M, D>.setValue(o: Record<M>, desc: KProperty<*>, value: SelectionValue<D>?)
    operator fun TextField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: String?)
    operator fun DateField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: LocalDate?)
    operator fun DateTimeField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: LocalDateTime?)
    operator fun TimeField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: LocalTime?)
    operator fun BooleanField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: Boolean?)
    operator fun DecimalField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: BigDecimal?)
    operator fun BinaryField<M>.setValue(o: Record<M>, desc: KProperty<*>, value: ByteArray?)
    operator fun <TM : BaseModel<TM>> Many2OneField<M, TM>.setValue(o: Record<M>, desc: KProperty<*>, value: Record<TM>?)
    operator fun <TM : BaseModel<TM>> One2ManyField<M, TM>.setValue(o: Record<M>, desc: KProperty<*>, value: Iterable<Record<TM>>)
    operator fun <TM : BaseModel<TM>> Many2ManyField<M, TM>.setValue(o: Record<M>, desc: KProperty<*>, value: Iterable<Record<TM>>)
    //endregion
}
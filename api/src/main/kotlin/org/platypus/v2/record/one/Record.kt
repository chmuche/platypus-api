package org.platypus.v2.record.one

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.utils.ContextAble
import org.platypus.v2.utils.Environmentable
import org.platypus.v2.utils.SudoAble

interface Record<M : BaseModel<M>> :
        SudoAble<Record<M>>,
        ContextAble<Record<M>>,
        Environmentable,
        ImmutableRecordField<M>,
        RecordMetaData,
        PersistedRecord<M>{

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


    /**
     * Update the current [Record] with the provided [RecordBuilder]
     * Then automatically update it in the database
     * This method is the only way to mutate a [Record]
     */
    fun update(builder: RecordBuilder<M>)
}
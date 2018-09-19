package org.platypus.v2.record.bag

import org.platypus.v2.ExecuteAutoLoad
import org.platypus.v2.INeedToDocumentThis
import org.platypus.v2.INeedToTestThis
import org.platypus.v2.LazyLoaded
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.impl.RecordImpl
import org.platypus.v2.utils.Environmentable
import org.platypus.v2.utils.ids

@INeedToTestThis
@INeedToDocumentThis
@LazyLoaded
interface Bag<M : BaseModel<M>> : Environmentable, List<Record<M>> {
    /**
     * The ids of the element containing by the Bag
     * The ids can be empty if this is an empty Bag or if the [loaded] is false
     */
    @ExecuteAutoLoad
    val ids: Collection<Int>

    /**
     * The model of the Bag
     */
    val model: M

}

class BagRecordImpl<M : BaseModel<M>>(
        override val env: PlatypusEnvironment,
        override val model: M,
        initIds: List<Int>) : Bag<M> {

    private val internalIds:MutableList<Int> = ArrayList(initIds)
    override val ids: Collection<Int>
        get() = internalIds

    override val size: Int
        get() = ids.size

    override fun contains(element: Record<M>): Boolean = element.id in ids

    override fun containsAll(elements: Collection<Record<M>>): Boolean = ids.containsAll(elements.ids)

    override fun get(index: Int): Record<M>  =RecordImpl(env, internalIds[index], model)


    override fun indexOf(element: Record<M>): Int = ids.indexOf(element.id)

    override fun isEmpty(): Boolean = ids.isEmpty()

    override fun iterator(): Iterator<Record<M>> {
        TODO("not implemented")
    }

    override fun lastIndexOf(element: Record<M>): Int = ids.lastIndexOf(element.id)

    override fun listIterator(): ListIterator<Record<M>> {
        TODO("not implemented")
    }

    override fun listIterator(index: Int): ListIterator<Record<M>> {
        TODO("not implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): Bag<M> {
        TODO("not implemented")
    }
}
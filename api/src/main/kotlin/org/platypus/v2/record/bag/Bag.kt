package org.platypus.v2.record.bag

import org.platypus.v2.ExecuteAutoLoad
import org.platypus.v2.INeedToDocumentThis
import org.platypus.v2.INeedToTestThis
import org.platypus.v2.LazyLoaded
import org.platypus.v2.model.BaseModel
import org.platypus.v2.record.one.Record
import org.platypus.v2.utils.Environmentable

@INeedToTestThis
@INeedToDocumentThis
@LazyLoaded
interface Bag<M : BaseModel<M>> : List<Record<M>>, Environmentable {
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

    /**
     *  True if the Bag has been loaded in the cache
     */
    val loaded: Boolean
}
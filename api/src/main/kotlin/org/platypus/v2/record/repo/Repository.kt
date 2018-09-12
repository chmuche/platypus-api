package org.platypus.v2.record.repo

import org.platypus.v2.model.BaseModel
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.utils.ContextAble
import org.platypus.v2.utils.Environmentable
import org.platypus.v2.utils.SudoAble

/**
 * A Repository is the class used to access to the persitence layer
 * You can add method to the Repository of a Model by using [ModelFunction.empty](api.empty)
 */
interface RecordRepository<M : BaseModel<M>> : Environmentable, SudoAble<RecordRepository<M>>, ContextAble<RecordRepository<M>> {


    fun search(): Bag<M>
}
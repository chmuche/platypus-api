package org.platypus.v2.record.repo

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.LinkModel

data class Many2manyToMerge(val link: LinkModel<*, *>, val targetIds:Set<Int>){
    var id:Int = 0
}

fun Iterable<Many2manyToMerge>.setId(id:Int){
    forEach { it.id = id }
}

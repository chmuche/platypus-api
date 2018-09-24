package org.platypus.v2.record.repo

import org.platypus.v2.model.LinkModel
import org.platypus.v2.model.field.reference.Many2OneField

data class Many2manyToMerge(val link: LinkModel<*, *>, val targetIds: Set<Int>)
data class One2ManyToMerge(val targetM2O: Many2OneField<*, *>, val targetIds: Set<Int>)

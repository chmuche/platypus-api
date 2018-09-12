package org.platypus.v2.model

import org.platypus.v2.db.database.DbDialect

data class ModelDDL(val struc: String, val alter: Set<String>)

interface DbModelDDL {
    fun createBaseModel(dbDialect: DbDialect): ModelDDL
}
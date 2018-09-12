package org.platypus.v2.model

import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.magic.IdField
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.utils.to_sneak_case

interface BaseModel<M : BaseModel<M>> : DbModelDDL {
    /**
     * The unique name of the model
     */
    val modelName: String

    val tableName: String
        get() = modelName.to_sneak_case().toLowerCase().replace('-', '_').replace('.', '_')

    val id: IdField<M>

    /**
     * This method is called the first time this model is loaded in the resgitry of platypus
     */
    fun load(){
        println("Load $modelName")
    }

    /**
     * The display fieldName of the models
     */
    val modelLabel: String
        get() = ""

    /**
     * A little help of what this models is used for
     */
    val modelHelp: String
        get() = ""

    /**
     * Contains all the field off this model
     */
    val fields: Set<BaseField<M, *>>
}

val <M : BaseModel<M>> M.storeFields: Set<BaseField<M, *>>
    get() = fields.filterTo(HashSet()) { it.store }
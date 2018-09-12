package org.platypus.v2.model

import org.platypus.v2.db.ReferenceOption
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.magic.IdField
import org.platypus.v2.model.field.magic.IdFieldImpl
import org.platypus.v2.model.field.reference.impl.Many2OneFieldImpl
import org.platypus.v2.utils.appendIf
import org.platypus.v2.utils.comma
import org.platypus.v2.utils.space
import kotlin.reflect.KProperty

infix fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>> M1.many2manyLink(m2: M2) = LinkModelDelegate(this, m2)
data class LinkModelDelegate<M1 : BaseModel<M1>, M2 : BaseModel<M2>>(val m1: M1, val m2: M2)
object ModelMany2Many {

    val m2mLink = HashMap<String, LinkModel<*, *>>()

    operator fun <M1 : BaseModel<M1>, M2 : BaseModel<M2>>
            LinkModelDelegate<M1, M2>.getValue(obj: ModelMany2Many?, kProperty: KProperty<*>): LinkModel<M1, M2> {
        val res = m2mLink.getOrPut(kProperty.name, { LinkModel(kProperty.name, { this.m1 }, { this.m2 }) })
        return res as LinkModel<M1, M2>
    }
}

class LinkModel<M1 : BaseModel<M1>, M2 : BaseModel<M2>>(
        override val modelName: String,
        private val m1: () -> M1,
        private val m2: () -> M2,
        val onDelete: ReferenceOption = ReferenceOption.SET_NULL
) : BaseModel<LinkModel<M1, M2>> {

    override val id: IdField<LinkModel<M1, M2>> = IdFieldImpl(this)
    override val fields: Set<BaseField<LinkModel<M1, M2>, *>>
        get() = setOf(id, m1M2O, m2M2O)

    internal val m1M2O by lazy { Many2OneFieldImpl("${m1().tableName}_id", this, m1()) }
    internal val m2M2O by lazy { Many2OneFieldImpl("${m2().tableName}_id", this, m2()) }


    val reverse: LinkModel<M2, M1> by lazy { LinkModel(modelName, m2, m1) }

//    @JvmName("findIdsR")
//    fun findIds(env: PlatypusEnvironment, prop: Many2ManyField<M2, M1>, modelID: ModelID): ModelIDS {
//        return if (env.internal.cache.isInDB(modelID)) {
//            val id = env.internal.cache.realID(modelID)
//            val q = this.slice(m1M2O).select(env) { m2M2O eq id.id }
//            m1M2O.target of q.map { it.get(m1M2O)!!.id }
//        } else {
//            m1M2O.target of emptyList()
//        }
//    }
//
//    fun findIds(env: PlatypusEnvironment, prop: Many2ManyField<M1, M2>, modelID: ModelID): ModelIDS {
//        return if (env.internal.cache.isInDB(modelID)) {
//            val id = env.internal.cache.realID(modelID)
//            val query = this.slice(this.id, this.m1M2O, this.m2M2O).select(env) { m1M2O eq id.id }
//            val ids = ArrayList<Int>()
//            for (row in query) {
//                env.internal.cache.store(this of row.get(this.id), setOf(this.id, this.m1M2O, this.m2M2O), row)
//                ids.add(row.get(m2M2O)!!.id)
//            }
//            m2M2O.target of ids
//        } else {
//            m2M2O.model of emptyList()
//        }
//    }

    override fun createBaseModel(dbDialect: DbDialect): ModelDDL {
        val builder = StringBuilder("CREATE TABLE")
        val constraint = HashSet<String>()
        builder.appendIf(dbDialect.supportsIfNotExists, " IF NOT EXISTS ")
        builder.append(dbDialect.identity(this))
        builder.space().append("(")
        val pk = this.id.createField(dbDialect)
        constraint.addAll(pk.constraint)
        builder.appendIf(dbDialect.debug, "\n\t")
        builder.append(pk.fieldDef)
        builder.comma()
        val fieldDDl1 = this.m1M2O.createField(dbDialect)
        constraint.addAll(fieldDDl1.constraint)
        builder.appendIf(dbDialect.debug, "\n\t")
        builder.append(fieldDDl1.fieldDef)
        builder.comma()
        val fieldDDl2 = this.m2M2O.createField(dbDialect)
        constraint.addAll(fieldDDl2.constraint)
        builder.appendIf(dbDialect.debug, "\n\t")
        builder.append(fieldDDl2.fieldDef)
        builder.appendIf(dbDialect.debug, "\n").append(")")
        return ModelDDL(builder.toString(), constraint)
    }

    override fun hashCode(): Int {
        return modelName.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinkModel<*, *>

        if (modelName != other.modelName) return false

        return true
    }
}
package org.platypus.v2.record.repo

import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.ModelMany2Many
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.record.one.MutableRecordBuilderToStore
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.one.RecordBuilder
import org.platypus.v2.record.one.RecordBuilderToStore
import org.platypus.v2.record.one.RecordBuilderToUpdate
import org.platypus.v2.record.one.impl.RecordBuilderToStoreImpl
import org.platypus.v2.record.one.impl.RecordImpl
import org.platypus.v2.security.PlatypusUser
import org.platypus.v2.utils.ContextAble
import org.platypus.v2.utils.Environmentable
import org.platypus.v2.utils.SudoAble

/**
 * A Repository is the class used to access to the persitence layer
 * You can add method to the Repository of a Model by using [ModelFunction.empty](api.empty)
 */
interface RecordRepository<M : BaseModel<M>> : Environmentable, SudoAble<RecordRepository<M>>, ContextAble<RecordRepository<M>> {

    val model: M

    /**
     * Create a new [RecordBuilder] with the [init] parameter applied to it
     */
    fun builderToStore(init: MutableRecordBuilderToStore<M>.() -> Unit = {}): RecordBuilderToStore<M>

    fun builderToUpdate(init: RecordBuilderToUpdate<M>.() -> Unit = {}): RecordBuilderToUpdate<M>

    /**
     * Store the [RecordBuilder] in the database and return the corresponding [Record]
     */
    fun store(builder: RecordBuilderToStore<M>): Record<M>
}

class RecordRepositoryImpl<M : BaseModel<M>>(override val env: PlatypusEnvironment, override val model: M) : RecordRepository<M> {

    override fun builderToStore(init: MutableRecordBuilderToStore<M>.() -> Unit): RecordBuilderToStore<M> {
        val builder = RecordBuilderToStoreImpl(emptyMap<BaseField<M, *>, Any?>())
        builder.init()
        return builder
    }

    override fun builderToUpdate(init: RecordBuilderToUpdate<M>.() -> Unit): RecordBuilderToUpdate<M> {
        TODO("not implemented")
    }

    override fun store(builder: RecordBuilderToStore<M>): Record<M> {
        val id = internalStore(builder)
        return RecordImpl(env, id, model)
    }

    private fun internalStore(builder: RecordBuilderToStore<M>): Int {
        val stmt = env.cr.dialect.insert(model)
        val many2ManyToMerge = ArrayList<Many2manyToMerge>()

        for ((key, value) in builder.rawData) {
            if (key.store && !key.isAutoInc) {
                stmt.forceSet(key, value)
            } else if (key is One2ManyField<*, *>) {
            } else if (key is Many2ManyField<*, *>) {
                val link = key.link(ModelMany2Many)
                many2ManyToMerge.add(Many2manyToMerge(link, value as Set<Int>))
            }
        }
        for (f in model.fields) {
            if (f.store && !f.isAutoInc && f !in builder.rawData.keys) {
                stmt.forceSet(f, f.defaultValueFun(env))
            }
        }
        env.cr.executor.execute(stmt)
        val id = stmt[model.id]
        processMany2ManyFieldsToStore(many2ManyToMerge, id)
        return id
    }

    private fun processMany2ManyFieldsToStore(many2ManyToMerge: ArrayList<Many2manyToMerge>, id: Int) {
        many2ManyToMerge.setId(id)
        for ((linkmodel, toInsert) in many2ManyToMerge.groupBy { it.link }) {
            for (otherId:Many2manyToMerge in toInsert) {
                for (idsToLink in otherId.targetIds){
                    val stmt1 = env.cr.dialect.insert(linkmodel)
                    if (linkmodel.m1M2O.target == model) {
                        stmt1.forceSet(linkmodel.m1M2O, id)
                        stmt1.forceSet(linkmodel.m2M2O, idsToLink)
                    } else {
                        stmt1.forceSet(linkmodel.m1M2O, idsToLink)
                        stmt1.forceSet(linkmodel.m2M2O, id)
                    }
                    env.cr.executor.execute(stmt1)
                }
            }
        }
    }

    override fun withContext(vararg vals: Pair<String, Any>): RecordRepository<M> {
        TODO("not implemented")
    }

    override fun sudo(user: PlatypusUser): RecordRepository<M> {
        TODO("not implemented")
    }
}
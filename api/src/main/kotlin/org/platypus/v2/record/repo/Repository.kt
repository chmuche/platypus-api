package org.platypus.v2.record.repo

import org.platypus.v2.db.predicate.InListPredicate
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.ModelMany2Many
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.reference.Many2ManyField
import org.platypus.v2.model.field.reference.One2ManyField
import org.platypus.v2.model.storeFields
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.bag.BagRecordImpl
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
    fun builderToStore(init: (MutableRecordBuilderToStore<M>) -> Unit = {}): RecordBuilderToStore<M>

    fun builderToUpdate(init: (RecordBuilderToUpdate<M>) -> Unit = {}): RecordBuilderToUpdate<M>

    /**
     * Store the [RecordBuilder] in the database and return the corresponding [Record]
     */
    fun store(builder: RecordBuilderToStore<M>): Record<M>

    fun store(builders: Iterable<RecordBuilderToStore<M>>): Bag<M>
}

class RecordRepositoryImpl<M : BaseModel<M>>(override val env: PlatypusEnvironment, override val model: M) : RecordRepository<M> {

    override fun builderToStore(init: (MutableRecordBuilderToStore<M>) -> Unit): RecordBuilderToStore<M> {
        val builder = RecordBuilderToStoreImpl(model, emptyMap())
        init(builder)
        return builder
    }

    override fun builderToUpdate(init: RecordBuilderToUpdate<M>.() -> Unit): RecordBuilderToUpdate<M> {
        TODO("not implemented")
    }

    override fun store(builder: RecordBuilderToStore<M>): Record<M> {
        val result = internalStore(builder)
        return RecordImpl(env, result, model)
    }

    override fun store(builders: Iterable<RecordBuilderToStore<M>>): Bag<M> {
        val records = builders.map { internalStore(it) }
        return BagRecordImpl(env, model, records)
    }

    private fun internalStore(builder: RecordBuilderToStore<M>): Int {
        val stmt = env.cr.dialect.insert(model)
        val many2ManyToMerge = ArrayList<Many2manyToMerge>()
        val one2ManyToMerge = ArrayList<One2ManyToMerge>()
        val builderWithDefault = builder.getBuilderWithDefault()
        for ((key, value) in builderWithDefault.rawData) {
            if (key.store && !key.isAutoInc) {
                stmt.forceSet(key, value)
            } else if (key is One2ManyField<*, *> && value != null) {
                one2ManyToMerge.add(One2ManyToMerge(key.targetField(), value as Set<Int>))
            } else if (key is Many2ManyField<*, *> && value != null) {
                val link = key.link(ModelMany2Many)
                many2ManyToMerge.add(Many2manyToMerge(link, value as Set<Int>))
            }
        }
        env.cr.executor.execute(stmt)
        val id = stmt[model.id]
        many2ManyToMerge.createMany2ManyLink(id)
        one2ManyToMerge.createOne2ManyLink(id)
//        env.cr.cache[model to id] = builderWithDefault
        return id
    }

    private fun RecordBuilderToStore<M>.getBuilderWithDefault(): RecordBuilderToStore<M> {
        val mapValueAndDefault = HashMap<BaseField<M, *>, Any?>(model.storeFields.size)
        for (f in model.fields) {
            if (f.store && !f.isAutoInc && f !in this.rawData.keys) {
                mapValueAndDefault[f] = f.defaultValueFun(env)
            } else {
                mapValueAndDefault[f] = this.rawData[f]
            }
        }
        return RecordBuilderToStoreImpl(model, mapValueAndDefault)
    }

    private fun List<Many2manyToMerge>.createMany2ManyLink(id: Int) {
        for (many2ManyToCreate: Many2manyToMerge in this) {
            val batchInsert = env.cr.dialect.batchInsert(many2ManyToCreate.link)
            for (idsToLink in many2ManyToCreate.targetIds) {
                if (many2ManyToCreate.link.m1M2O.target == model) {
                    batchInsert.forceSet(many2ManyToCreate.link.m1M2O, id)
                    batchInsert.forceSet(many2ManyToCreate.link.m2M2O, idsToLink)
                } else {
                    batchInsert.forceSet(many2ManyToCreate.link.m1M2O, idsToLink)
                    batchInsert.forceSet(many2ManyToCreate.link.m2M2O, id)
                }
                batchInsert.addBatch()
            }
            env.cr.executor.execute(batchInsert)
        }
    }

    private fun List<One2ManyToMerge>.createOne2ManyLink(id: Int) {
        for (otherId in this) {
            val updateStmt = env.cr.dialect.update(otherId.targetM2O.model,
                    InListPredicate(otherId.targetM2O.model.id, otherId.targetIds))
            updateStmt.forceSet(otherId.targetM2O, id)
            env.cr.executor.execute(updateStmt)
        }

    }

    override fun withContext(vararg vals: Pair<String, Any>): RecordRepository<M> {
        TODO("not implemented")
    }

    override fun sudo(user: PlatypusUser): RecordRepository<M> {
        TODO("not implemented")
    }
}
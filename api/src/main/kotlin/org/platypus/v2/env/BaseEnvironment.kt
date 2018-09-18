package org.platypus.v2.env

import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.model.Model
import org.platypus.v2.record.one.Record
import org.platypus.v2.record.repo.RecordRepository
import org.platypus.v2.security.PlatypusUser
import java.time.ZoneId
import java.util.logging.Logger

val ROOT_USER = object : PlatypusUser {}

class BaseEnvironment private constructor(
        override val envUser: PlatypusUser,
        override val sudoUser: PlatypusUser,
        override val context: PlatypusContext,
        override val cr: Transaction
) : PlatypusEnvironment {

    internal companion object {
        fun create(user: PlatypusUser?, context: PlatypusContext, newTransaction: Transaction): BaseEnvironment {
            val userUsed = user ?: ROOT_USER
            return BaseEnvironment(userUsed, userUsed, context, newTransaction)
        }
    }

    init {
//        envUser.getData(this).fetch()
//        sudoUser.getData(this).fetch()
//        internal.cr.stat.reset()
    }

    override fun connect(user: PlatypusUser): PlatypusEnvironment {
        return BaseEnvironment(user, user, context, cr)
    }

    override val timezone: ZoneId
        get() = ZoneId.systemDefault()
    override val debug: Boolean = true

    override val logger: Logger = Logger.getLogger("GLOBAL LOGGER")

    fun models(name: String): Model<*> {
        TODO()
    }

    override fun <M : Model<M>> repoOf(model: M): RecordRepository<M> {
        TODO("not implemented")
    }

    override fun <M : Model<M>> emptyRecordOf(model: M): Record<M> {
        TODO("not implemented")
    }

    override fun withContext(vararg vals: Pair<String, Any>): PlatypusEnvironment {
        TODO()
    }

    override fun sudo(user: PlatypusUser): PlatypusEnvironment {
        TODO()
    }


    override fun flush() {
        flushCache()
    }

    override fun flush(model: Model<*>) {
//        for (toinsert in cache.toInsert.filter { it.model == model }) {
//            flushInsert(toinsert)
//        }
//        for (toUpdate in cache.toUpdate.filter { it.key == model }.values) {
//            flushUpdate(toUpdate, model)
//        }

    }

    private fun flushCache() {
//        for (toinsert in cache.toInsert) {
//            flushInsert(toinsert)
//        }
//        for ((model, toUpdate) in cache.toUpdate) {
//            flushUpdate(toUpdate, model)
//        }
//        cache.reset()
    }

//    private fun flushUpdate(toUpdate: Map<Int, Set<IModelField<*, *>>>, model: IModel<*>) {
//        for ((id, fieldsToUpdate) in toUpdate) {
//            model.storeFields
//            val realId = cache.realID(model of id)
//            val updateStatement = UpdateStatement(this, model, model.id eq realId.id)
//            for (fieldToUpdate in fieldsToUpdate) {
//                val value = if (fieldToUpdate.type.typeEnum.isRelationalField()) {
//                    val cacheValue = cache.getValue(model, id, fieldToUpdate).second as ModelID?
//                    if (cacheValue != null) {
//                        cache.realID(cacheValue)
//                    } else {
//                        null
//                    }
//                } else {
//                    cache.getValue(model, id, fieldToUpdate).second
//                }
//                updateStatement.forceSet(fieldToUpdate, value)
//            }
//            updateStatement.execute()
//        }
//    }

//    private fun flushInsert(toinsert: ModelID) {
//        if (cache.isInDB(toinsert)) {
//            return
//        }
//        val values = cache[toinsert]
//        val insertStmt = InsertStatement<Int>(this, toinsert.model)
//        for ((k, v) in values) {
//            when (k.type.typeEnum) {
//                MANY_TO_ONE, ONE_TO_ONE -> {
//                    if (v != null) {
//                        if (cache.isNotInDB(v as ModelID)) {
//                            if (!cache.isToUpdate(toinsert, k)) {
//                                flushInsert(v)
//                                insertStmt.forceSet(k, cache.realID(v).id)
//                            }
//                        } else {
//                            insertStmt.forceSet(k, cache.realID(v).id)
//                        }
//                    } else {
//                        insertStmt.forceSet(k, k.defaultValueFun?.invoke(this))
//                    }
//                }
//                REV_ONE_TO_ONE, MANY_TO_MANY -> {
//
//                }
//                PK -> {
//                    if (toinsert.model == Users && v == 1) {
//                        insertStmt.forceSet(k, v)
//                    }
//                }
//                else ->
//                    if (k.store) {
//                        if (v == null) {
//                            insertStmt.forceSet(k, k.defaultValueFun?.invoke(this))
//                        } else {
//                            insertStmt.forceSet(k, v)
//                        }
//                    }
//            }
//        }
//        insertStmt.execute()
//        val newId = insertStmt[toinsert.model.id]
//        cache.addnewId(toinsert, newId)
//    }

    override fun close() {
        println("Close Env in mode ${cr.mode}")
        flush()
//        internal.cr.close()
    }
}
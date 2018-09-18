package org.platypus.v2.server

import org.platypus.v2.db.SchemaCreator
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.database.SqlTransactionFactory
import org.platypus.v2.db.database.TransactionMode
import org.platypus.v2.db.database.dialect.postgres.PostgreSQLDialect
import org.platypus.v2.env.BaseEnvironment
import org.platypus.v2.env.ErpModule
import org.platypus.v2.env.PlatypusContext
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.ROOT_USER
import org.platypus.v2.module.PlatypusModule
import org.platypus.v2.modules.base.BaseModule
import java.sql.SQLException


class PlatypusServer private constructor(
        private val crFactory: SqlTransactionFactory
) {
    private var started: Boolean = false
    
    
    fun <T> inManagedTransaction(block: (Transaction) -> T?):T?{
        val tr = crFactory.newTransaction(TransactionMode.AUTO_COMMIT)
        var result:T? = null
        try {
            result = block(tr)
            tr.commit()
        } catch (e: SQLException) {
            tr.rollback()
            throw e
        } finally {
            tr.close()
        }
        return result
    }

    fun <T> inManagedEnvironment(block: (PlatypusEnvironment) -> T?):T?{
        val tr = crFactory.newTransaction(TransactionMode.AUTO_COMMIT)
        val result: T?
        val env = BaseEnvironment.create(ROOT_USER, PlatypusContext.newContext(), tr)
        try {
            result = block(env)
            tr.commit()
        } catch (e: SQLException) {
            tr.rollback()
            throw e
        } finally {
            tr.close()
        }
        return result
    }

    companion object {
        /**
         * Initialise a PlatypusServer
         * The server returned is not started yet and no Sql connection is open
         * @see start
         */
        fun install(module: PlatypusModule): PlatypusServer {
            val crFactory = SqlTransactionFactory.connect(PostgreSQLDialect,
                    url = "jdbc:postgresql://localhost:5432/platypus_test",
                    driver = "org.postgresql.Driver",
                    user = "platypus",
                    password = "platypus")
            module.loadModule()
            val server = PlatypusServer(crFactory)
            server.inManagedTransaction {
                val creator = SchemaCreator(it)
                creator.create(ErpModule.models)
            }
            return server
        }

        private fun PlatypusModule.loadDepends() {
            val deps = if (depends.isEmpty() && moduleName != "base") setOf(BaseModule) else depends
            for (module in deps) {
                module.loadModule()
            }
        }

        private fun PlatypusModule.loadModule() {
            if (this !in ErpModule.loadedModule) {
                this.loadDepends()
                for (mod in this.models) {
                    ErpModule.models.add(mod)
                }
            }
            ErpModule.loadedModule.add(this)
        }

    }


}
package org.platypus.v2.db.database

import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.cr.TransactionRecordCache
import org.platypus.v2.db.database.dialect.SqlDialectFactory
import java.sql.Connection
import java.sql.DriverManager

enum class TransactionMode {
    AUTO_COMMIT, MANUAL, READ_ONLY, ROLLBACK
}

private class TransactionApiImpl(
        override val mode: TransactionMode,
        private val connection: Connection,
        override val dialect: DbDialect
) : Transaction, Connection by connection {

    override val executor: StatementExecutor = StatementExecutor(this)
    override val dbName: String
        get() = connection.catalog

    override val cache: TransactionRecordCache
        get() = TODO("not implemented")

    override fun commit() {
        if (mode == TransactionMode.AUTO_COMMIT || mode == TransactionMode.MANUAL) {
            println("Commit connection h:" + this.hashCode())
            connection.commit()
        }
    }

    override fun rollback() {
        println("Rollback connection h:" + this.hashCode())
        connection.rollback()
    }

    override fun close() {
        println("Close connection h:" + this.hashCode())
        connection.close()
    }
}

class SqlTransactionFactory constructor(dialectFactory: SqlDialectFactory, val connector: () -> Connection) {

    val dialect: DbDialect = dialectFactory.create(connector().metaData)

    fun newTransaction(mode: TransactionMode): Transaction {
        val connection = connector()
        connection.autoCommit = false
        connection.transactionIsolation = Connection.TRANSACTION_REPEATABLE_READ
        connection.isReadOnly = mode == TransactionMode.READ_ONLY
        return TransactionApiImpl(mode, connection, dialect)
    }

    companion object {
        fun connect(dialect: SqlDialectFactory, url: String, driver: String, user: String = "", password: String = "", onOpenConnection: (Connection) -> Unit = {}): SqlTransactionFactory {
            println("Connect to $url as $user")
            Class.forName(driver).newInstance()

            return SqlTransactionFactory(dialect) {
                DriverManager.getConnection(url, user, password).apply { onOpenConnection(this) }
            }
        }
    }
}
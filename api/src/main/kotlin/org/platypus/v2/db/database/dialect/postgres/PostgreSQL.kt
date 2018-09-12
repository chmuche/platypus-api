package org.platypus.v2.db.database.dialect.postgres

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.database.dialect.SqlDialect
import org.platypus.v2.db.database.dialect.SqlDialectFactory
import org.platypus.v2.db.database.DDLUtil
import java.sql.DatabaseMetaData


internal class PostgreSQLDialect private constructor(metadata: DatabaseMetaData) : SqlDialect("postgres", metadata) {

    companion object : SqlDialectFactory {
        override fun create(meta: DatabaseMetaData) = PostgreSQLDialect(meta)

    }

    override val needsSequenceToAutoInc: Boolean = true

    override val ddlUtil: DDLUtil = object : DDLUtil, DbDialect by this {}
}
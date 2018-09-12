package org.platypus.v2.db.database.dialect

import org.platypus.v2.db.database.DbDialect
import java.sql.DatabaseMetaData

interface SqlDialectFactory {
    fun create(meta: DatabaseMetaData): DbDialect
}
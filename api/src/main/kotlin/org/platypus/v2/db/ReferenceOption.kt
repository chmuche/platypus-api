package org.platypus.v2.db

import org.platypus.v2.db.database.DbDialect

enum class ReferenceOption : SqlAble {
    RESTRICT {
        override fun toSql(dialect: DbDialect, query: QueryBuilder) = " ON DELETE RESTRICT"
    },
    CASCADE {
        override fun toSql(dialect: DbDialect, query: QueryBuilder) = " ON DELETE CASCADE"
    },
    SET_NULL {
        override fun toSql(dialect: DbDialect, query: QueryBuilder) = " ON DELETE SET NULL"

    },
    NO_ACTION {
        override fun toSql(dialect: DbDialect, query: QueryBuilder) = ""
    }
//    ,SET_DEFAULT {
//        override fun toSQL(query: QueryBuilder) = " ON DELETE SET DEFAULT"
//    }
}
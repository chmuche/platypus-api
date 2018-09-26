package org.platypus.v2.db.struc

import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.field.api.DbFieldDDL

interface SqlColumn : DbFieldDDL {
    val columnName: String
    val columnType: String
    val tableHolder: String
    val indexed: Boolean
    val required: Boolean
    val unique: Boolean
    val foreignKeyTo: String
    val foreignContraint: String

    fun describe(dbDialect: DbDialect): String
    fun alias(alias: String): SqlColumn
}

interface SqlColumnWrapper {
    fun unWrap(): SqlColumn
}

interface SqlTable {
    val tableName: String
    val tableType: String
    val columns: Set<SqlColumn>
    val checkConstraint: Set<String>
    val uniqueConstrainte: Set<String>
    val indexes: Set<String>

    fun describe(dbDialect: DbDialect): String

    fun alias(alias: String): SqlTable
}

interface SqlTableWrapper {
    fun unWrap(): SqlTable
}
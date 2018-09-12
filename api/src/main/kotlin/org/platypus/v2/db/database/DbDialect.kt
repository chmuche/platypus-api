package org.platypus.v2.db.database

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.db.ReferenceOption
import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction

interface DbDialect {
    val dialectName: String
    fun identity(mod: BaseModel<*>): String
    fun identity(mod: BaseField<*, *>): String

    val ddlUtil: DDLUtil
    val debug :Boolean
        get() = true

    fun supportsSelectForUpdate(): Boolean
    val supportsMultipleGeneratedKeys: Boolean

    // --> REVIEW
    val supportsIfNotExists: Boolean
    val supportsMultipleResultSets: Boolean
    val needsSequenceToAutoInc: Boolean
    val needsQuotesWhenSymbolsInNames: Boolean
    val identifierLengthLimit: Int
    fun catalog(transaction: Transaction): String
    // <-- REVIEW

    val defaultReferenceOption: ReferenceOption
    val identityQuoteString: String
    val shouldQuoteIdentifiers: Boolean
    val supportsAlterTableWithAddColumn: Boolean
    val blobAsStream: Boolean
    val keywords: Set<String>

    // Specific SQL statements

    fun insert(table: BaseModel<*>, columns: List<BaseField<*, *>>, expr: String, cr: StatementExecutor): String
    fun delete(table: BaseModel<*>, where: String?, cr: StatementExecutor): String
    fun replace(table: BaseModel<*>, data: List<Pair<BaseField<*, *>, Any?>>, cr: StatementExecutor): String

    fun createIndex(unique: Boolean, tableName: String, indexName: String, columns: List<String>): String
    fun dropIndex(tableName: String, indexName: String): String
    fun modifyColumn(column: BaseField<*, *>): String

    fun limit(size: Int, offset: Int = 0, alreadyOrdered: Boolean = true): String
    fun inProperCase(s: String): String
    fun needQuotes(token: String): Boolean = true
    fun booleanFromStringToBoolean(value: String): Boolean
    fun booleanToStatementString(b: Boolean): String
}
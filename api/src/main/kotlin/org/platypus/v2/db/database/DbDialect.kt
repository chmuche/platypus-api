package org.platypus.v2.db.database

import org.platypus.v2.db.ReferenceOption
import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.cr.statements.BatchInsertStatement
import org.platypus.v2.db.cr.statements.DeleteStatement
import org.platypus.v2.db.cr.statements.InsertStatement
import org.platypus.v2.db.cr.statements.UpdateStatement
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField

interface DbDialect {
    val dialectName: String
    fun identity(mod: BaseModel<*>): String
    fun identity(mod: BaseField<*, *>): String

    val ddlUtil: DDLUtil
    val debug: Boolean
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

    val DEFAULT_VALUE_EXPRESSION: String get() = "DEFAULT VALUES"

    fun insert(table: BaseModel<*>): InsertStatement<Int>
    fun batchInsert(table: BaseModel<*>): BatchInsertStatement
    fun delete(table: BaseModel<*>, where: Predicate? = null): DeleteStatement
    fun update(table: BaseModel<*>, where: Predicate? = null, limit: Int? = null): UpdateStatement

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
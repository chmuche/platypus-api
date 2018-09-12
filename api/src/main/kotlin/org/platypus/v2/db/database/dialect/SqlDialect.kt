package org.platypus.v2.db.database.dialect

import org.platypus.v2.db.ANSI_SQL_2003_KEYWORDS
import org.platypus.v2.db.ReferenceOption
import org.platypus.v2.db.cr.StatementExecutor
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.utils.token
import java.math.BigDecimal
import java.sql.DatabaseMetaData
import java.util.*

abstract class SqlDialect(override val dialectName: String, private val metadata: DatabaseMetaData) : DbDialect, DatabaseMetaData by metadata {
    override val supportsIfNotExists: Boolean
        get() = true
    override val needsSequenceToAutoInc: Boolean
        get() = false
    override val needsQuotesWhenSymbolsInNames: Boolean
        get() = true
    override val identifierLengthLimit: Int
        get() = 60

    override fun catalog(transaction: Transaction): String = transaction.catalog


    override val defaultReferenceOption: ReferenceOption
        get() = TODO("not implemented")
    val url: String = metadata.url
    val version = BigDecimal("${metadata.databaseMajorVersion}.${metadata.databaseMinorVersion}")
    fun isVersionCovers(version: BigDecimal) = this.version >= version
    override val keywords = ANSI_SQL_2003_KEYWORDS + metadata.sqlKeywords.split(',')
    override val identityQuoteString = metadata.identifierQuoteString!!.trim()
    override val supportsAlterTableWithAddColumn = metadata.supportsAlterTableWithAddColumn()
    override val supportsMultipleResultSets = metadata.supportsMultipleResultSets()
    override val shouldQuoteIdentifiers = !metadata.storesMixedCaseQuotedIdentifiers() && metadata.supportsMixedCaseQuotedIdentifiers()

    private val checkedIdentities = object : LinkedHashMap<String, Boolean>(100) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Boolean>?): Boolean = size >= 1000
    }

    override fun needQuotes(identity: String): Boolean {
        return checkedIdentities.getOrPut(identity.toLowerCase()) {
            keywords.any { identity.equals(it, true) } || !identity.isIdentifier()
        }
    }

    fun String.quoted() = "$identityQuoteString$this$identityQuoteString"
    fun String.quotedIfNeeded() = if (needQuotes(this)) "$identityQuoteString$this$identityQuoteString" else this

    private fun String.isIdentifier() = !isEmpty() && first().isIdentifierStart() && all { it.isIdentifierStart() || it in '0'..'9' }
    private fun Char.isIdentifierStart(): Boolean = this in 'a'..'z' || this in 'A'..'Z' || this == '_' || this in extraNameCharacters

    override fun identity(mod: BaseModel<*>): String = quoteIfNecessary(inProperCase(mod.tableName))

    override fun identity(mod: BaseField<*, *>): String {
        val nameInProperCase = inProperCase(mod.fieldName)
        return if (shouldQuoteIdentifiers && nameInProperCase != mod.fieldName)
            mod.fieldName.quoted
        else quoteIfNecessary(nameInProperCase)
    }

    internal fun quoteIfNecessary(identity: String): String {
        if (identity.contains('.'))
            return identity.split('.').joinToString(".") { quoteTokenIfNecessary(it) }
        else {
            return quoteTokenIfNecessary(identity)
        }
    }

    private fun quoteTokenIfNecessary(token: String): String = if (needQuotes(token)) token.quoted else token

    private val String.quoted get() = "${identityQuoteString}$this${identityQuoteString}".trim()

    override val blobAsStream: Boolean = true

    override fun booleanFromStringToBoolean(value: String): Boolean = value.toBoolean()

    override fun booleanToStatementString(b: Boolean): String = b.toString()

    override fun supportsSelectForUpdate(): Boolean = true

    override val supportsMultipleGeneratedKeys: Boolean = false

    private val isUpperCaseIdentifiers = metadata.storesUpperCaseIdentifiers()
    private val isLowerCaseIdentifiers = metadata.storesLowerCaseIdentifiers()
    val String.inProperCase: String
        get() = when {
            isUpperCaseIdentifiers -> toUpperCase()
            isLowerCaseIdentifiers -> toLowerCase()
            else -> this
        }

    protected open val DEFAULT_VALUE_EXPRESSION = "DEFAULT VALUES"

    override fun insert(table: BaseModel<*>, columns: List<BaseField<*, *>>, expr: String, cr: StatementExecutor): String {
        val (columnsExpr, valuesExpr) = if (columns.isNotEmpty()) {
            columns.joinToString(prefix = "(", postfix = ")") { identity(it) } to expr
        } else "" to DEFAULT_VALUE_EXPRESSION

        return "INSERT INTO ${identity(table)} $columnsExpr $valuesExpr"
    }

    override fun delete(table: BaseModel<*>, where: String?, cr: StatementExecutor): String {
        return buildString {
            token("DELETE")
            token("FROM")
            token(identity(table))
            if (where != null) {
                token("WHERE")
                append(where)
            }
        }
    }

    override fun replace(table: BaseModel<*>, data: List<Pair<BaseField<*, *>, Any?>>, cr: StatementExecutor): String {
        TODO("not implemented")
    }

    override fun createIndex(unique: Boolean, tableName: String, indexName: String, columns: List<String>): String {
        TODO("not implemented")
    }

    override fun dropIndex(tableName: String, indexName: String): String {
        TODO("not implemented")
    }

    override fun modifyColumn(column: BaseField<*, *>): String {
        TODO("not implemented")
    }

    override fun limit(size: Int, offset: Int, alreadyOrdered: Boolean): String {
        return "LIMIT $size"
    }

    override fun inProperCase(s: String) = s.inProperCase
}
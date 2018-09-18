package org.platypus.v2.db.cr.statements

import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.db.cr.StatementType
import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.magic.IdField
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * isIgnore is supported for mysql only
 */
open class InsertStatement<Key : Any>(
        dialect: DbDialect,
        val table: BaseModel<*>,
        val avoidAutoInc: Boolean = true
) : UpdateBuilder<Int>(dialect, StatementType.INSERT, listOf(table)) {
    open protected val flushCache = true
    var generatedKey: Key? = null

    infix operator fun get(idPk: IdField<*>): Int = generatedKey as? Int ?: error("No key generated")

    open protected fun generatedKeyFun(rs: ResultSet?, inserted: Int): Key? {
        return autoIncColumns.firstOrNull().let { column ->
            if (rs?.next() == true) {
                @Suppress("UNCHECKED_CAST")
                column!!.convertFromDB(rs.getObject(1)) as Key
            } else null
        }
    }

    override fun prepareSQL(): String {
        val builder = QueryBuilder(true)
        val values = if (avoidAutoInc) {
            arguments().first().filter { !it.first.isAutoInc }
        } else {
            arguments().first()
        }
        val sql = if (values.isEmpty()) {
            ""
        } else values.joinToString(prefix = "VALUES (", postfix = ")") { (col, value) ->
            builder.registerArgument(dialect, col, value)
        }
        val columns = values.map { it.first }
        val (columnsExpr, valuesExpr) = if (columns.isNotEmpty()) {
            columns.joinToString(prefix = "(", postfix = ")") { dialect.identity(it) } to sql
        } else "" to dialect.DEFAULT_VALUE_EXPRESSION

        return "INSERT INTO ${dialect.identity(table)} $columnsExpr $valuesExpr"
    }

    override fun PreparedStatement.executeInternal(): Int {
        val inserted = if (arguments().count() > 1 || isAlwaysBatch) executeBatch().sum() else executeUpdate()
        return inserted.apply {
            val rs = if (autoIncColumns.isNotEmpty()) {
                generatedKeys
            } else null
            generatedKey = generatedKeyFun(rs, this)
        }
    }

    protected val autoIncColumns = targets.flatMap { it.fields }.filter { it.isAutoInc && it.store }

    override fun prepared(cr: Transaction, sql: String): PreparedStatement {
        return if (autoIncColumns.isNotEmpty()) {
            // http://viralpatel.net/blogs/oracle-java-jdbc-get-primary-key-insert-sql/
            cr.prepareStatement(sql, autoIncColumns.map { dialect.identity(it) }.toTypedArray())!!
        } else {
            cr.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)!!
        }
    }

    override fun arguments() = listOf(values.map { it.key to it.value })
}

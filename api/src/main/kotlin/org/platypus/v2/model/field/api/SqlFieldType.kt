package org.platypus.v2.model.field.api

import org.platypus.v2.db.database.DbDialect
import java.sql.PreparedStatement
import java.sql.ResultSet

internal object DefaultValueMarker {
    override fun toString(): String = "DEFAULT"
}

/**
 * This interface contains all the needed method to convert a value to/from the DB layer
 */
interface DbFieldConverter {
    val required: Boolean
    val fieldName: String

    /**
     * Convert the value from the DB to the value to the Cache
     */
    fun convertFromDB(value: Any): Any = value

    /**
     * Convert the value to String
     */
    fun convertToString(value: Any?): String = when (value) {
        null -> {
            if (required) error("NULL in required column $fieldName")
            "NULL"
        }
        DefaultValueMarker -> "DEFAULT"
        is Iterable<*> -> {
            value.joinToString(",") { convertToString(it) }
        }
        else -> {
            convertNotNullValueToString(value)
        }
    }

    /**
     * Convert the value to String
     */
    fun convertNotNullValueToString(value: Any): String = convertNotNullToDB(value).toString()

    /**
     * Convert the value from the Cache to the value to the appropriate format of the DB
     */
    fun convertToDB(value: Any?): Any? = value?.let { convertNotNullToDB(it) }

    /**
     * Convert the value from the Cache to the value to the appropriate format of the DB
     */
    fun convertNotNullToDB(value: Any): Any = value

    /**
     * This method is called when The [ResultSet] of a query is converted to platypus Record
     */
    fun readObject(rs: ResultSet, index: Int): Any? = rs.getObject(index)

    /**
     * This method is called when To convert the value to a [PreparedStatement] parameter
     */
    fun setParameter(stmt: PreparedStatement, index: Int, value: Any?) {
        stmt.setObject(index, value)
    }
}

data class FieldDDL(val fieldDef: String, val constraint: Set<String>)

interface DbFieldDDL{
    fun createField(dbDialect: DbDialect):FieldDDL
    fun deleteField(dbDialect: DbDialect):FieldDDL
}

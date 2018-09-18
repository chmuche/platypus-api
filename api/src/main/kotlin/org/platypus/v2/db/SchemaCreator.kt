package org.platypus.v2.db

import org.platypus.v2.db.cr.Transaction
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.ModelDDL
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.util.ArrayList
import java.util.HashMap
import kotlin.collections.HashSet

class SchemaCreator(private val cr :Transaction) {

    private val utils = SchemaCreatorUtils(cr.dbName, cr.metaData)

    fun create(tables: Set<BaseModel<*>>) {
        val statements = createStatements(tables)
        for (statement in statements) {
            println(statement.struc)
            cr.executor.nativeWithNb(statement.struc)
        }
        for (statement in statements) {
            for (alter in statement.alter) {
                println(alter)
                cr.executor.nativeWithNb(alter)
            }
        }
    }

    private fun findDependsModel(models: Set<BaseModel<*>>): Set<BaseModel<*>> {
        val set = HashSet<BaseModel<*>>(models)
        for (model in models) {
            set.addAll(recursiveFindDependsModel(model, set))
        }
        return set
    }

    private fun recursiveFindDependsModel(m: BaseModel<*>, n: HashSet<BaseModel<*>>): Set<BaseModel<*>> {
        for (f in m.fields) {
            for (ref in f.usedModels.second){
                if (ref !in n) {
                    n.add(ref)
                    n.addAll(recursiveFindDependsModel(ref, n))
                }
            }
        }
        return n
    }

    fun createStatements(tables: Set<BaseModel<*>>): Set<ModelDDL> {
        val statements = HashSet<ModelDDL>()
        val allModels: Set<BaseModel<*>> = findDependsModel(tables)

        if (allModels.isEmpty())
            return statements


        for (table in allModels) {
            table.load()
            if (!utils.modelExist(table)){
                statements.add(table.createBaseModel(cr.dialect))
            }
        }

        return statements
    }
}
/**
 * type:
 * @see java.sql.Types
 */
data class ColumnMetadata(val name: String, val type: Int, val nullable: Boolean)

data class ModelMetadata(val name: String, val type: String)

private class SchemaCreatorUtils(
        databaseName: String,
        metadata: DatabaseMetaData
) {
    private val allTablesNames: List<ModelMetadata>
    private val allViewNames: List<ModelMetadata>
    private val ddlMetadata: Map<ModelMetadata, List<ColumnMetadata>>

    init {
        allTablesNames = ArrayList()
        allViewNames = ArrayList()
        val resultSet = metadata.getTables(null, null, "%", arrayOf("TABLE", "VIEW"))
        while (resultSet.next()) {
            if (resultSet.getString("TABLE_TYPE") == "TABLE") {
                allTablesNames.add(ModelMetadata(resultSet.getString("TABLE_NAME").inProperCase, "TABLE"))
            }
            if (resultSet.getString("TABLE_TYPE") == "VIEW") {
                allViewNames.add(ModelMetadata(resultSet.getString("TABLE_NAME").inProperCase, "VIEW"))
            }
        }
        resultSet.close()
        val rs = metadata.getColumns(databaseName, null, "%", "%")
        ddlMetadata = rs.extractColumns(allTablesNames) {
            it.getString("TABLE_NAME") to ColumnMetadata(it.getString("COLUMN_NAME"), it.getInt("DATA_TYPE"), it.getBoolean("NULLABLE"))
        }
        rs.close()
    }

    private val isUpperCaseIdentifiers = metadata.storesUpperCaseIdentifiers()
    private val isLowerCaseIdentifiers = metadata.storesLowerCaseIdentifiers()

    val String.inProperCase: String
        get() = when {
            isUpperCaseIdentifiers -> toUpperCase()
            isLowerCaseIdentifiers -> toLowerCase()
            else -> this
        }

    private fun ResultSet.extractColumns(tables: List<ModelMetadata>, extract: (ResultSet) -> Pair<String, ColumnMetadata>): Map<ModelMetadata, List<ColumnMetadata>> {
        val mapping = tables.associateBy { it.name }
        val result = HashMap<ModelMetadata, MutableList<ColumnMetadata>>()

        while (next()) {
            val (tableName, columnMetadata) = extract(this)
            mapping[tableName]?.let { t ->
                result.getOrPut(t) { arrayListOf() } += columnMetadata
            }
        }
        return result
    }


//    /**
//     * returns map of constraint for a models name/column name pair
//     */
//
//    fun columnConstraints(vararg tables: BaseModel<*>): Map<Pair<String, String>, List<ForeignKeyConstraint>> = emptyMap()

//    /**
//     * return fieldSet of indices for each models
//     */
//    fun existingIndices(vararg tables: BaseModel<*>): Map<BaseModel<*>, List<Index>> = emptyMap()

    fun modelExist(model: BaseModel<*>): Boolean = model.toMetadata() in allTablesNames


    private fun BaseModel<*>.toMetadata(): ModelMetadata {
        return ModelMetadata(tableName.inProperCase, "TABLE")
    }

    fun checkTableMapping(table: BaseModel<*>) = true


}

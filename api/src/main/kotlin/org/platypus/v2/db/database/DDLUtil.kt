package org.platypus.v2.db.database

import org.platypus.v2.db.OrmConstraint
import org.platypus.v2.db.QueryBuilder
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.model.field.api.FieldDDL
import org.platypus.v2.model.field.api.ReferenceField
import org.platypus.v2.utils.space
import org.platypus.v2.utils.token

interface DDLUtil : DbDialect {

    fun StringBuilder.NotNull(field: BaseField<*, *>) =
            if (field.required) {
                this.space().append("NOT NULL")
            } else this


    fun StringBuilder.check(dbDialect: DbDialect, ck: OrmConstraint<*>): StringBuilder {
        space()
        append("CONSTRAINT")
        space()
        append(ck.name)
        space()
        append("CHECK(")
        append(ck.toSql(dbDialect, QueryBuilder(false)))
        append(")")
        return this
    }

    fun StringBuilder.unique(field: BaseField<*, *>) =
            if (field.unique) {
                space().append("CONSTRAINT").space().append(field.fieldName).append("_uniq").space().append("UNIQUE")
            } else this


    fun createClassicFieldDDL(field: BaseField<*, *>, fieldType: StringBuilder.() -> Unit): FieldDDL {
        return FieldDDL(buildString {
            token(identity(field))
            fieldType()
            NotNull(field)
            unique(field)
        }, field.constraint.map { StringBuilder().check(this, it).toString() }.toSet())
    }

    fun createReferenceFieldDDL(field: ReferenceField<*, *>): FieldDDL {
        val ddl = createClassicFieldDDL(field) { append("INTEGER") }
        val refConstraint = buildString {
            token("ALTER TABLE")
            token(identity(field.model))
            token("ADD")
            token("CONSTRAINT")
            append(field.fieldName).append("_").append(inProperCase(field.target.tableName)).append("_fk").space()
            token("FOREIGN KEY")
            append("(").append(identity(field)).token(")")
            token("REFERENCES")
            token(inProperCase(field.target.tableName))
            append("(").append(inProperCase(field.target.id.fieldName)).append(")").space()
            append(field.referenceOption.toSql(this@DDLUtil, QueryBuilder(false)))
        }
        return FieldDDL(ddl.fieldDef, ddl.constraint + refConstraint)
    }
}
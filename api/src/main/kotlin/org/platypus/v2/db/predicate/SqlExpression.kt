package org.platypus.v2.db.predicate

import org.platypus.v2.db.SqlQueryParameter
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.utils.token

interface SqlExpression {

    fun toSql(dialect: DbDialect, q: SqlQueryParameter): String
}

interface Predicate : SqlExpression
interface ComparePredicate : Predicate {
    val field: BaseField<*, *>
    val value: Any?
    val operator: String
}

internal object PredicateUtils {

    fun comparaisonOp(compPredicate: ComparePredicate, dialect: DbDialect, q: SqlQueryParameter) = buildString {
        append(toSql(compPredicate.field, dialect))
        token(compPredicate.operator)
        append(q.registerArgument(compPredicate.field, compPredicate.value))
    }

    fun toSql(field: BaseField<*, *>, dialect: DbDialect) = dialect.identity(field.model) + "." + dialect.identity(field)
}

class EqPredicate(override val field: BaseField<*, *>, override val value: Any?) : ComparePredicate {
    override val operator: String = "="
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class LikePredicate(override val field: BaseField<*, *>, override val value: Any?, caseSensitive: Boolean) : ComparePredicate {
    override val operator: String = if (caseSensitive) {
        "ILIKE"
    } else {
        "LIKE"
    }

    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class NotLikePredicate(override val field: BaseField<*, *>, override val value: Any?, caseSensitive: Boolean) : ComparePredicate {
    override val operator: String = if (caseSensitive) {
        "NOT ILIKE"
    } else {
        "NOT LIKE"
    }

    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class NEqPredicate(override val field: BaseField<*, *>, override val value: Any?) : ComparePredicate {
    override val operator: String = "!="
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class LessPredicate(override val field: BaseField<*, *>, override val value: Any?) : ComparePredicate {
    override val operator: String = "<"
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class LessEqPredicate(override val field: BaseField<*, *>, override val value: Any?) : ComparePredicate {
    override val operator: String = "=<"
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class GreaterPredicate(override val field: BaseField<*, *>, override val value: Any?) : ComparePredicate {
    override val operator: String = ">"
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class GreaterEqPredicate(override val field: BaseField<*, *>, override val value: Any?) : ComparePredicate {
    override val operator: String = ">="
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = PredicateUtils.comparaisonOp(this, dialect, q)
}

class AndPredicate(val predicate1: Predicate, val predicate2: Predicate) : Predicate {
    val operator: String = "AND"
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = buildString {
        append(predicate1.toSql(dialect, q))
        token(operator)
        append(predicate2.toSql(dialect, q))
    }
}

class OrPredicate(val predicate1: Predicate, val predicate2: Predicate) : Predicate {
    val operator: String = "OR"
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = buildString {
        append(predicate1.toSql(dialect, q))
        token(operator)
        append(predicate2.toSql(dialect, q))
    }
}

class InListPredicate(val field: BaseField<*, *>, val iterable: Iterable<Any>) : Predicate {
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = this.InNotInPredicateToSql(true, dialect, q)
}

class NotInListPredicate(field: BaseField<*, *>, iterable: Iterable<Any>) : Predicate {
    private val inlist = InListPredicate(field, iterable)
    override fun toSql(dialect: DbDialect, q: SqlQueryParameter): String = inlist.InNotInPredicateToSql(false, dialect, q)
}

private fun InListPredicate.InNotInPredicateToSql(isInList: Boolean, dialect: DbDialect, q: SqlQueryParameter): String = buildString {
    iterable.iterator().let { i ->
        val first = i.next()
        PredicateUtils.toSql(field, dialect)
        if (!i.hasNext()) {
            when {
                isInList -> append(" = ")
                else -> append(" != ")
            }
            append(q.registerArgument(field, first))
        } else {
            when {
                isInList -> append(" IN (")
                else -> append(" NOT IN (")
            }
            q.registerArguments(field, iterable).joinTo(this)
            append(")")
        }
    }
}
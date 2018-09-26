package org.platypus.v2.model

import org.platypus.v2.db.cr.statements.select.FieldPredicate
import org.platypus.v2.db.database.DbDialect
import org.platypus.v2.db.predicate.AndPredicate
import org.platypus.v2.db.predicate.EqPredicate
import org.platypus.v2.db.predicate.GreaterEqPredicate
import org.platypus.v2.db.predicate.GreaterPredicate
import org.platypus.v2.db.predicate.InListPredicate
import org.platypus.v2.db.predicate.LessEqPredicate
import org.platypus.v2.db.predicate.LessPredicate
import org.platypus.v2.db.predicate.LikePredicate
import org.platypus.v2.db.predicate.NEqPredicate
import org.platypus.v2.db.predicate.NotInListPredicate
import org.platypus.v2.db.predicate.NotLikePredicate
import org.platypus.v2.db.predicate.OrPredicate
import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.field.api.BaseField

interface Alias<M : BaseModel<M>> : BaseModel<M>, FieldPredicate<M> {
    val originalModel: M
    val alias: String

    override val tableName: String get() = "${originalModel.tableName} AS $alias"
}

interface FieldAlias<M : BaseModel<M>, T : Any> : BaseField<M, T> {
    val modelAlias: Alias<M>
    val original: BaseField<M, T>
    val alias:String
}

class GenericFieldAliasImpl<M : BaseModel<M>, T : Any>(
        override val modelAlias: Alias<M>,
        override val original: BaseField<M, T>,
        override val alias: String = original.fieldName
) : FieldAlias<M, T>, BaseField<M, T> by original{

    override fun completeName(dbDialect: DbDialect?): String {
        return if (dbDialect != null){
            "${modelAlias.alias}.$alias"
        } else {
            "${modelAlias.alias}.$alias"
        }
    }
}

class AliasImpl<M : BaseModel<M>>(override val originalModel: M, override val alias: String) : BaseModel<M> by originalModel, Alias<M> {

    override val tableName: String
        get() = super<Alias>.tableName


    private fun <T : Any> BaseField<M, T>.wrap():BaseField<M, T>{
        return GenericFieldAliasImpl(this@AliasImpl, this)
    }

    //Classic Predicate
    override fun <T : Any> BaseField<M, T>.eq(value: T?): EqPredicate = EqPredicate(this.wrap(), value)
    override fun <T : Any> BaseField<M, T>.neq(value: T?) = NEqPredicate(this.wrap(), value)
    override fun <T : Any> BaseField<M, T>.inList(value: Iterable<T>) = InListPredicate(this.wrap(), value)
    override fun <T : Any> BaseField<M, T>.notInList(value: Iterable<T>) = NotInListPredicate(this.wrap(), value)
    override fun <T : Comparable<T>> BaseField<M, T>.ge(value: T?) = GreaterPredicate(this.wrap(), value)
    override fun <T : Comparable<T>> BaseField<M, T>.geEq(value: T?) = GreaterEqPredicate(this.wrap(), value)
    override fun <T : Comparable<T>> BaseField<M, T>.less(value: T?) = LessPredicate(this.wrap(), value)
    override fun <T : Comparable<T>> BaseField<M, T>.lessEq(value: T?) = LessEqPredicate(this.wrap(), value)
    override fun <T : CharSequence> BaseField<M, T>.ilike(value: T?) = LikePredicate(this.wrap(), value, false)
    override fun <T : CharSequence> BaseField<M, T>.like(value: T?) = LikePredicate(this.wrap(), value?.let { "'$it'" }, true)
    override fun <T : CharSequence> BaseField<M, T>.notIlike(value: T?) = NotLikePredicate(this.wrap(), value?.let { "'$it'" }, true)
    override fun <T : CharSequence> BaseField<M, T>.notLike(value: T?) = NotLikePredicate(this.wrap(), value, false)
    override fun <T : CharSequence> BaseField<M, T>.contains(value: T?) = LikePredicate(this.wrap(), value?.let { "'%$it%'" }, false)
    override fun <T : CharSequence> BaseField<M, T>.notContains(value: T?) = NotLikePredicate(this.wrap(), value?.let { "'%$it%'" }, false)
    override fun <T : CharSequence> BaseField<M, T>.startWith(value: T?) = LikePredicate(this.wrap(), value?.let { "'$it%'" }, false)
    override fun <T : CharSequence> BaseField<M, T>.notStartWith(value: T?) = NotLikePredicate(this.wrap(), value?.let { "'$it%'" }, false)
    override fun <T : CharSequence> BaseField<M, T>.endWith(value: T?) = LikePredicate(this.wrap(), value?.let { "'%$it'" }, false)
    override fun <T : CharSequence> BaseField<M, T>.notEndWith(value: T?) = NotLikePredicate(this.wrap(), value?.let { "'%$it'" }, false)
}
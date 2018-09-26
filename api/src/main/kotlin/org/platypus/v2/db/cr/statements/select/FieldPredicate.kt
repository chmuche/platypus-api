package org.platypus.v2.db.cr.statements.select

import org.platypus.v2.db.predicate.Predicate
import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField

interface FieldPredicate<M : BaseModel<M>> {
    //Classic Predicate
    fun <T : Any> BaseField<M, T>.eq(value: T?) : Predicate

    fun <T : Any> BaseField<M, T>.neq(value: T?) : Predicate
    fun <T : Any> BaseField<M, T>.inList(value: Iterable<T>) : Predicate
    fun <T : Any> BaseField<M, T>.notInList(value: Iterable<T>) : Predicate
    //Comparable predicate
    fun <T : Comparable<T>> BaseField<M, T>.ge(value: T?) : Predicate

    fun <T : Comparable<T>> BaseField<M, T>.geEq(value: T?) : Predicate
    fun <T : Comparable<T>> BaseField<M, T>.less(value: T?) : Predicate
    fun <T : Comparable<T>> BaseField<M, T>.lessEq(value: T?) : Predicate

    //String Predicate
    fun <T : CharSequence> BaseField<M, T>.ilike(value: T?) : Predicate

    fun <T : CharSequence> BaseField<M, T>.like(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.notIlike(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.notLike(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.contains(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.notContains(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.startWith(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.notStartWith(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.endWith(value: T?) : Predicate
    fun <T : CharSequence> BaseField<M, T>.notEndWith(value: T?) : Predicate
}
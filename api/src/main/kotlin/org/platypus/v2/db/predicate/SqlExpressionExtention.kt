package org.platypus.v2.db.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField

//Classic Preddicate
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.eq(value: T?) = EqPredicate(this, value)

fun <M : BaseModel<M>, T : Any> BaseField<M, T>.neq(value: T?) = NEqPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.inList(value: Iterable<T>) = InListPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.notInList(value: Iterable<T>) = NotInListPredicate(this, value)
//Comparable predicate
fun <M : BaseModel<M>, T : Comparable<T>> BaseField<M, T>.ge(value: T?) = GreaterPredicate(this, value)

fun <M : BaseModel<M>, T : Comparable<T>> BaseField<M, T>.geEq(value: T?) = GreaterEqPredicate(this, value)
fun <M : BaseModel<M>, T : Comparable<T>> BaseField<M, T>.less(value: T?) = LessPredicate(this, value)
fun <M : BaseModel<M>, T : Comparable<T>> BaseField<M, T>.lessEq(value: T?) = LessEqPredicate(this, value)

//String Predicate
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.ilike(value: T?) = LikePredicate(this, value, false)

fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.like(value: T?) = LikePredicate(this, value, true)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.notIlike(value: T?) = NotLikePredicate(this, value, true)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.notLike(value: T?) = NotLikePredicate(this, value, false)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.contains(value: T?) = LikePredicate(this, value?.let { "%$it%" }, false)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.notContains(value: T?) = NotLikePredicate(this, value?.let { "%$it%" }, false)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.startWith(value: T?) = LikePredicate(this, value?.let { "$it%" }, false)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.notStartWith(value: T?) = NotLikePredicate(this, value?.let { "$it%" }, false)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.endWith(value: T?) = LikePredicate(this, value?.let { "%$it" }, false)
fun <M : BaseModel<M>, T : CharSequence> BaseField<M, T>.notEndWith(value: T?) = NotLikePredicate(this, value?.let { "%$it" }, false)


fun Predicate.and(predicate: Predicate) = AndPredicate(this, predicate)
fun Predicate.or(predicate: Predicate) = OrPredicate(this, predicate)

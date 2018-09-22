package org.platypus.v2.db.predicate

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField

fun <M : BaseModel<M>, T : Any> BaseField<M, T>.eq(value: T?) = EqPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.neq(value: T?) = NEqPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.inList(value: Iterable<T>) = InListPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.notInList(value: Iterable<T>) = NotInListPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.ge(value: T?) = GreaterPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.geEq(value: T?) = GreaterEqPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.less(value: T?) = LessPredicate(this, value)
fun <M : BaseModel<M>, T : Any> BaseField<M, T>.lessEq(value: T?) = LessEqPredicate(this, value)

fun Predicate.and(predicate: Predicate) = AndPredicate(this, predicate)
fun Predicate.or(predicate: Predicate) = OrPredicate(this, predicate)

package org.platypus.v2.db

import org.platypus.v2.model.BaseModel
import org.platypus.v2.model.field.api.BaseField
import org.platypus.v2.utils.Namable
import org.platypus.v2.utils.Validatable

interface OrmConstraint<T> : Namable, SqlAble, Validatable<T>


//class CheckConstraint<M : BaseModel<M>>(val name: String, private val msg: String?, val check: M.() -> PredicateField) {
//    val errorMsg: String by lazy {
//        msg ?: name
//    }
//}
//
//class UniqueConstraint<M : BaseModel<M>>(val name: String, private val msg: String?, val fields: Set<BaseField<M, *>>) {
//    val errorMsg: String by lazy {
//        msg ?: name
//    }
//}
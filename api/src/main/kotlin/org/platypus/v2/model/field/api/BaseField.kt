package org.platypus.v2.model.field.api

import org.platypus.v2.ValidatableValue
import org.platypus.v2.ValidatableValueThrow
import org.platypus.v2.ValidateException
import org.platypus.v2.model.BaseModel
import org.platypus.v2.visitor.BaseFieldVisitor

interface BaseField<M : BaseModel<M>, T : Any> :
        Comparable<BaseField<*, *>>,
        FieldSlots<T>,
        ValidatableValue<T>,
        ValidatableValueThrow<T>,
        DbFieldConverter,
        DbFieldDDL {
    val model: M
    val isAutoInc:Boolean
        get() = false

    val usedModels:Pair<M, Set<BaseModel<*>>>


    fun <PARAM_TYPE, RETURN> accept(visitor: BaseFieldVisitor<PARAM_TYPE, RETURN>, p: PARAM_TYPE): RETURN

    override fun compareTo(other: BaseField<*, *>): Int {
        TODO("not implemented")
    }

    fun anyToType(value: Any): T = value as T

    private val completeName
        get() = "$model.$fieldName"

    fun validateUnsafe(value: Any?): Set<String> {
        val errors = HashSet<String>()
        if (value == null) {
            if (required) {
                errors.add("The field $completeName can't be null")
            }
        } else {
            errors.addAll(constraint.flatMap { it.validate(anyToType(value)) })
        }
        return errors
    }

    override fun validateNullable(value: T?): Set<String> {
        val errors = HashSet<String>()
        if (value == null) {
            if (required) {
                errors.add("The field $completeName can't be null")
            }
        } else {
            errors.addAll(constraint.flatMap { it.validate(value) })
        }
        return errors
    }

    override fun validateAndThrow(value: T?) {
        val res = this.validateUnsafe(value)
        if (res.isNotEmpty()) {
            throw ValidateException(res)
        }
    }
}
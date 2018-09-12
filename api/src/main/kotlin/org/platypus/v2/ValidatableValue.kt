package org.platypus.v2

interface ValidatableValue<in TYPE : Any> {

    /**
     * Check if the value is value, and return a set of possible error
     * This method doesn't throw any [Exception]
     * @see ValidatableValueThrow if you want to throw errors
     */
    fun validateNullable(value: TYPE?): Set<String>
}

interface ValidatableValueThrow<in TYPE : Any> {

    /**
     * Check if the value is value, and throw a [ValidateException]
     * This method doesn't throw any [Exception]
     * @see ValidatableValue if you don't want to throw errors
     */
    fun validateAndThrow(value: TYPE?)
}

interface ValidatableType{

    /**
     * Check if the the current instance is valid, and return a set of possible error
     * This method doesn't throw any [Exception]
     */
    fun validate(): Set<String>
}

interface ValidValueUnTyped{

    /**
     * Check if the value is value, and return a set of possible error
     * This method doesn't throw any [Exception]
     * @see ValidatableValueThrow if you want to throw errors
     */
    fun validateNullable(value: Any?): Set<String>
}

class ValidateException(s: String) : IllegalStateException(s) {
    constructor(s: Collection<String>) : this(s.joinToString("\n"))
}
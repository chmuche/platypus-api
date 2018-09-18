package org.platypus.v2.utils

import org.platypus.v2.env.PlatypusContext
import org.platypus.v2.env.PlatypusEnvironment
import org.platypus.v2.env.ReadOnlyPlatypusEnvironment
import org.platypus.v2.model.BaseModel
import org.platypus.v2.record.bag.Bag
import org.platypus.v2.record.one.Record
import org.platypus.v2.security.PlatypusUser

interface Identifiable {
    val id: Int
}

interface TypedCloneable<T> : Cloneable {
    fun typedClone(): T
    override fun clone(): Any = typedClone() as Any
}

interface Mergable<M : Mergable<M>> {

    /**
     * Merge two object of the same type
     * The return object object can be [this] or a new instance
     * see the doc of the impl to be sure
     */
    fun merge(other: M): M
}

interface Namable {
    val name: String
}

interface Environmentable {
    val env: PlatypusEnvironment
}

interface ContextAble<out T> {
    fun withContext(vararg vals: Pair<String, Any>): T
}

interface SudoAble<out T> {
    fun sudo(user: PlatypusUser): T
}

interface Validatable<T> {
    fun validate(value: T): Set<String>
}

interface ValidatableNullable<in T> {
    fun validateNullable(value: T): Set<String>
}

interface EnvironementFactory {

    fun newEnv(user: PlatypusUser? = null, ctx: PlatypusContext? = null): PlatypusEnvironment

    fun newReadOnlyEnv(user: PlatypusUser? = null, ctx: PlatypusContext? = null): ReadOnlyPlatypusEnvironment

    fun newFakeEnv(user: PlatypusUser? = null, ctx: PlatypusContext? = null): PlatypusEnvironment
}

val <M : BaseModel<M>> Iterable<Record<M>>.ids: List<Int>
    get() {
        return if (this is Bag<*>) {
            this.ids.toList()
        } else {
            this.map { it.id }
        }
    }
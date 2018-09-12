package org.platypus.v2.utils

import java.io.StringReader


private fun String.reader(): StringReader = StringReader(this)

fun StringBuilder.space(nb: Int = 1) = append(" " * nb)
fun StringBuilder.token(token: String) = append(token).space()

fun StringBuilder.appendValues(values: Collection<String>) = values.forEach { append(it) }
fun StringBuilder.append(vararg values: String) = values.forEach { append(it) }
fun StringBuilder.comma() = append(", ")
fun StringBuilder.enter() = append("\n")
fun StringBuilder.appendIf(predicate: Boolean, value: String) = if (predicate) append(value) else this
fun StringBuilder.tab(i: Int = 0) = append("\t" * i)

operator fun CharSequence.times(i: Int): CharSequence {
    val sb = StringBuilder()
    var j = 0
    while (j < i) {
        sb.append(this)
        j++
    }
    return sb.toString()
}


/**
 * Calls the specified function [block] with the given [receiver] as its receiver and returns its result.
 */
inline fun <T : AutoCloseable, R> withAutoClose(receiver: T, block: (T) -> R): R {
    val res = block(receiver)
    receiver.close()
    return res
}

fun String.suffix(s: String): String {
    return if (this.endsWith(s)) this else this + s
}

fun String.prefix(s: String): String {
    return if (this.startsWith(s)) this else s + this
}

fun String.wrap(prefix: String, suffix: String): String {
    return this.prefix(prefix).suffix(suffix)
}

fun String.to_sneak_case(): String = this.replace(Regex("(.)(\\p{Upper})"), "$1_$2")

fun String?.containsOrFalse(other: CharSequence, ignoreCase: Boolean = false): Boolean {
    return this?.contains(other, ignoreCase) ?: false
}

fun String?.containsOrTrue(other: CharSequence, ignoreCase: Boolean = false): Boolean {
    return this?.contains(other, ignoreCase) ?: true
}

fun <E> MutableCollection<E>.addIfNotNull(element: E?) {
    if (element != null) {
        add(element)
    }
}

fun <E> MutableCollection<E>.addIf(element: E, predicate: E.() -> Boolean) {
    if (element.predicate()) {
        add(element)
    }
}
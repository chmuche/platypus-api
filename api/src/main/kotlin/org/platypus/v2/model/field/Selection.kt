package org.platypus.v2.model.field

import kotlin.reflect.KProperty

data class SelectionValue<S : Selection<S>>(val value: String, val label: String)
abstract class Selection<S : Selection<S>> {
    private val _selection = HashMap<String, String>()

    operator fun String.getValue(receiver: S, property: KProperty<*>): SelectionValue<S> {
        _selection[property.name] = this
        return SelectionValue(property.name, this)
    }

    internal fun getUnsafe(value: String?): SelectionValue<S>? {
        if (value != null && value in _selection) {
            return SelectionValue(value, _selection[value]!!)
        }
        return null
    }
}

object MySelection : Selection<MySelection>() {

}
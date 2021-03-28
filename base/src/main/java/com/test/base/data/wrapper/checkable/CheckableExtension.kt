package com.test.base.data.wrapper.checkable

import com.test.base.data.wrapper.DataWrapper

fun <T, E> Collection<E>.toggleChecked(predicate: (T) -> Boolean)
        where E : DataWrapper<T>, E : CheckableDataInterface {
    filterAndApply(this, { predicate(it) }, { it.toggleChecked() })
}

fun <T, E> Collection<E>.toggleChecked(value: T)
        where E : DataWrapper<T>, E : CheckableDataInterface {
    toggleChecked(predicate = { it == value })
}

private fun <T, E : DataWrapper<T>> filterAndApply(
    collection: Collection<E>,
    filterPredicate: (T) -> Boolean,
    applyConsumer: (E) -> Unit
) {
    collection
        .filter { filterPredicate(it.data) }
        .forEach { applyConsumer(it) }
}
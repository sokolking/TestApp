package com.test.base.data.wrapper.selectable

import com.test.base.data.wrapper.DataWrapper

fun <T, E> Collection<E>.setSelected(predicate: (T) -> Boolean)
        where E : DataWrapper<T>, E : SelectableDataInterface {
    setDeselected()
    findSingleAndApply({ predicate(it) }, { it.isSelected = true })
}

fun <T, E> Collection<E>.setDeselected()
        where E : DataWrapper<T>, E : SelectableDataInterface {
    forEach {
        it.apply {
            this.isSelected = false
        }
    }
}

fun <T, E> Collection<E>.getSelectedOrNull(): E?
        where E : DataWrapper<T>, E : SelectableDataInterface = firstOrNull { it.isSelected }

private fun <T, E : DataWrapper<T>> Collection<E>.findSingleAndApply(
    findPredicate: (T) -> Boolean,
    applyConsumer: (E) -> Unit
) {
    val foundedItems = filter { findPredicate(it.data) }
    if (foundedItems.size > 1) {
        throw IllegalStateException("было найдено больше одного элемента")
    } else {
        foundedItems.forEach(applyConsumer)
    }
}
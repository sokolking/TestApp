package com.test.base.ext

fun <T> List<T>.copyListAndReplaceItem(
    predicate: (T) -> Boolean,
    duplicator: (T) -> T,
): List<T> {
    val newList = this.toMutableList()
    val itemPosition = newList.indexOfFirst(predicate)
    if (itemPosition != -1) {
        val copiedItem = duplicator(newList[itemPosition])
        newList.removeAt(itemPosition)
        newList.add(itemPosition, copiedItem)
    }
    return newList
}

fun <T> List<T>.copyListAndReplaceItem(
    position: Int,
    duplicator: (T) -> T,
): List<T> {
    val newList = this.toMutableList()
    val copiedItem = duplicator(newList[position])
    newList.removeAt(position)
    newList.add(position, copiedItem)
    return newList
}

fun <T> MutableList<T>.findAndRemove(predicate: (T) -> Boolean): T? {
    val index = indexOfFirst(predicate)
    return if (index == -1) {
        null
    } else {
        removeAt(index)
    }
}
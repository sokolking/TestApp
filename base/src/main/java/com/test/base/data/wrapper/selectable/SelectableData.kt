package com.test.base.data.wrapper.selectable

import com.test.base.data.wrapper.DataWrapper

/**
 * contains only 1 selected item
 */
data class SelectableData<T>(
    override var data: T,
    override var isSelected: Boolean = false
) : DataWrapper<T>,
    SelectableDataInterface

interface SelectableDataInterface {

    var isSelected: Boolean

    fun toggleSelected() {
        isSelected = !isSelected
    }
}
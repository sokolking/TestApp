package com.test.base.data.wrapper.checkable

import com.test.base.data.wrapper.DataWrapper

interface CheckableDataInterface {

    var isChecked: Boolean

    fun toggleChecked() {
        isChecked = !isChecked
    }
}

data class CheckableData<T>(
    override var data: T,
    override var isChecked: Boolean = false
) : DataWrapper<T>, CheckableDataInterface
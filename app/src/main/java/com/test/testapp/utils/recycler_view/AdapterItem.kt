package com.test.testapp.utils.recycler_view

abstract class AdapterItem(val viewType: Int = 0, val neverUpdates: Boolean = false) {
    open fun isItemSame(other: AdapterItem): Boolean {
        return neverUpdates && this::class == other::class
    }

    open fun isContentSame(other: AdapterItem): Boolean {
        return true
    }

    open fun getChangePayload(other: AdapterItem): Any? {
        return null
    }
}
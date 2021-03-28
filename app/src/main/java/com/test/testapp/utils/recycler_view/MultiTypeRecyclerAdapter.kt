package com.test.testapp.utils.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class MultiTypeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var items = emptyList<AdapterItem>()
    private var registeredItemTypes = emptyMap<Int, AdapterItem>()

    fun update(newItems: List<AdapterItem>) {
        val callback = DiffUtilCallback(items, newItems)

        items = newItems
        registeredItemTypes = items.associateBy { it.viewType }

        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createViewHolder(parent,
                registeredItemTypes[viewType] ?: throw IllegalStateException())
    }

    final override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    protected fun inflate(parent: ViewGroup, layoutRes: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

    final override fun getItemCount(): Int {
        return items.size
    }

    fun <T : AdapterItem> getItem(position: Int): T {
        return items[position] as T
    }

    protected abstract fun createViewHolder(parent: ViewGroup, item: AdapterItem): RecyclerView.ViewHolder
}
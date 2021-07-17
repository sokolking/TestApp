package com.test.testapp.features.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.domain.api.comments.ExplorerData
import com.test.testapp.R
import com.test.testapp.utils.recycler_view.AdapterItem
import com.test.testapp.utils.recycler_view.MultiTypeRecyclerAdapter
import kotlinx.android.synthetic.main.item_explorer.view.*

class ExplorerAdapter : MultiTypeRecyclerAdapter() {

    override fun createViewHolder(parent: ViewGroup, item: AdapterItem): RecyclerView.ViewHolder {
        return when (item) {
            is ExplorerItem -> {
                SearchRouteViewHolder(inflate(parent, R.layout.item_explorer))
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchRouteViewHolder -> {
                holder.bind(getItem<ExplorerItem>(position).explorerData)
            }
        }
    }

    fun showExplorers(explorerData: List<ExplorerData>) {
        val items = mutableListOf<AdapterItem>()
        explorerData.forEach { route ->
            items.add(ExplorerItem(route))
        }
        update(items)
    }

    class ExplorerItem(val explorerData: ExplorerData) : AdapterItem(ADAPTER_ITEM_EXPLORER) {
        override fun isItemSame(other: AdapterItem): Boolean {
            return explorerData.imageRes == (other as? ExplorerItem)?.explorerData?.imageRes
        }

        override fun isContentSame(other: AdapterItem): Boolean {
            return explorerData.title == (other as? ExplorerItem)?.explorerData?.title
        }
    }

    inner class SearchRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val context = view.context
        private var currentExplorerData: ExplorerData? = null
        private val picture = view.iv_picture
        private val title = view.tv_title
        private val job = view.tv_job
        private val location = view.tv_location
        private val type = view.tv_type
        private val salary = view.tv_salary
        private val percent = view.tv_percent

        fun bind(explorerData: ExplorerData) {
            currentExplorerData = explorerData
            picture.setImageResource(explorerData.imageRes)
            title.text = explorerData.title
            job.text = explorerData.job
            location.text = explorerData.location
            type.text = explorerData.type
            salary.text = explorerData.salary
            percent.text = explorerData.percent
        }
    }

    companion object {
        const val ADAPTER_ITEM_EXPLORER = 1
    }
}
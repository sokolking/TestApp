package com.test.testapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.test.testapp.R
import com.test.testapp.enum.ExplorerTabs
import kotlinx.android.synthetic.main.view_explorer_tab.view.*

class ExplorerTabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_explorer_tab, this)
        v_find_jobs.apply {
            onItemSelected = {
                this@ExplorerTabView.v_find_jobs.isSelected(true)
                this@ExplorerTabView.v_help_others.isSelected(false)
                this@ExplorerTabView.v_find_candidates.isSelected(false)
            }
        }.render(ExplorerTabs.FIND_JOBS.explorerTabItem)
        v_help_others.apply {
            onItemSelected = {
                this@ExplorerTabView.v_find_jobs.isSelected(false)
                this@ExplorerTabView.v_help_others.isSelected(true)
                this@ExplorerTabView.v_find_candidates.isSelected(false)
            }
        }.render(ExplorerTabs.HELP_OTHERS.explorerTabItem)
        v_find_candidates.apply {
            onItemSelected = {
                this@ExplorerTabView.v_find_jobs.isSelected(false)
                this@ExplorerTabView.v_help_others.isSelected(false)
                this@ExplorerTabView.v_find_candidates.isSelected(true)
            }
        }.render(ExplorerTabs.FIND_CANDIDATES.explorerTabItem)
    }

}
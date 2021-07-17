package com.test.testapp.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.test.common.ExplorerTabItem
import com.test.testapp.R
import kotlinx.android.synthetic.main.view_explorer_tab_item.view.*

class ExplorerTabItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    var onItemSelected: (ExplorerTabItem) -> Unit = {}
    var currentItem: ExplorerTabItem? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_explorer_tab_item, this)
    }

    fun render(explorerTabItem: ExplorerTabItem) {
        currentItem = explorerTabItem
        v_root.setOnClickListener {
            explorerTabItem.let(onItemSelected)
        }
        tv_title.text = context.getString(explorerTabItem.textRes)
        isSelected(explorerTabItem.isSelected)
    }

    fun isSelected(isSelected: Boolean) {
        iv_icon.setBackgroundResource(
            if (isSelected) {
                currentItem?.selectedImageRes ?: 0
            } else {
                currentItem?.imageRes ?: 0
            }
        )
        if (isSelected) {
            updateView(Typeface.BOLD, R.drawable.ic_selected_tab_background)
        } else {
            updateView(Typeface.NORMAL, R.drawable.ic_unselected_tab_background)
        }
    }

    private fun updateView(typeface: Int, background: Int) {
        tv_title.setTypeface(null, typeface)
        iv_background.setBackgroundResource(background)
    }

}
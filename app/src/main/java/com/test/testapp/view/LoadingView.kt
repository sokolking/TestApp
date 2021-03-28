package com.test.testapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import com.test.base.ext.dpToPx
import com.test.testapp.R
import com.test.testapp.ext.resColor

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    init {
        val progress = ProgressBar(context)
        val lp = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        addView(
            progress,
            lp
        )
        progress.isIndeterminate = true
        ViewCompat.setElevation(this, 16.dpToPx.toFloat())
        setBackgroundColor(resColor(R.color.black_50))
        isClickable = true
    }
}
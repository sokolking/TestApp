package com.test.testapp.ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Outline
import android.graphics.Paint
import android.text.Editable
import android.view.View
import android.view.ViewOutlineProvider
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputLayout
import com.test.base.ext.dpToPx
import com.test.testapp.R

fun View.roundTopCorners(radiusDp: Int) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = radiusDp.dpToPx
            outline.setRoundRect(
                0,
                0,
                view.measuredWidth,
                view.measuredHeight + radius,
                radius.toFloat()
            )
        }
    }
    clipToOutline = true
}

fun View.roundAllCorners(radiusDp: Int) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val radius = radiusDp.dpToPx
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, radius.toFloat())
        }
    }
    clipToOutline = true
}

fun View.resizeWidth(percent: Double, margins: Int? = null) {
    layoutParams = layoutParams.apply {
        width = ((resources.displayMetrics.widthPixels - (margins ?: 0).dpToPx) * percent).toInt()
    }
}

fun View.resColor(@ColorRes color: Int): Int = context.resColor(color)

fun View.resColorState(@ColorRes color: Int): ColorStateList = context.resColorState(color)

fun View.setRoundedBgWithColor(
    @ColorRes color: Int,
    cornersDp: Int
) {
    background = MaterialShapeDrawable(
        ShapeAppearanceModel
            .builder()
            .setAllCornerSizes(cornersDp.dpToPx.toFloat())
            .build()
    ).apply {
        fillColor = ColorStateList.valueOf(resColor(color))
    }
}

fun EditText.afterTextChanged(
    textInputLayout: TextInputLayout,
    action: (text: Editable?) -> Unit
) {
    doAfterTextChanged {
        if (!it.isNullOrEmpty()) {
            val blueColor = resColorState(R.color.color_input_border_text_entered)
            textInputLayout.setBoxStrokeColorStateList(blueColor)
            textInputLayout.defaultHintTextColor = blueColor
        } else {
            val defaultColor = resColorState(R.color.color_input_border)
            textInputLayout.setBoxStrokeColorStateList(defaultColor)
            textInputLayout.defaultHintTextColor = defaultColor
        }
        action(it)
    }
}

fun View.hideKeyboard() {
    val token = windowToken ?: return
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.hideSoftInputFromWindow(token, 0)
}

fun View.showKeyboard() {
    requestFocus()
    val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    manager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun <V : View, D> V.actionIfChanged(data: D?, action: V.(data: D?) -> Unit) {
    if (updateTag(data)) {
        action(data)
    }
}

fun <V : View, D> V.updateTag(data: D?): Boolean {
    val hash = data?.hashCode() ?: 0
    return if (tag != hash) {
        tag = hash
        true
    } else {
        false
    }
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags = if (show) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

fun EditText.updateText(newText: String?) {
    if (text?.toString() != newText) {
        setText(newText)
        setSelection(length())
    }
}
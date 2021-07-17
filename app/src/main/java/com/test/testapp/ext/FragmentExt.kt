package com.test.testapp.ext

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.base.ext.doOnApplyWindowInsets
import com.test.base.ext.updateSystemWindowInsets

fun Fragment.resColor(@ColorRes color: Int): Int = requireContext().resColor(color)

fun Fragment.hideKeyboard() {
    view?.hideKeyboard()
}

fun BottomSheetDialogFragment.setTopRoundedBgWithColor(
    @ColorRes color: Int,
    cornersDp: Int
) {
    val parentView = view?.parent as? View ?: return
    parentView.post {
        parentView.roundTopCorners(cornersDp)
        parentView.setBackgroundColor(resColor(color))
    }
}

fun initDefaultInsetsFragment(root: View) {
    root.doOnApplyWindowInsets { view, insets ->
        view.updatePadding(
            top = insets.systemWindowInsetTop,
            bottom = insets.systemWindowInsetBottom
        )
        insets.updateSystemWindowInsets(top = 0, bottom = 0)
    }
}
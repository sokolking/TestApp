package com.test.testapp.ext

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.test.testapp.R

inline fun Activity.finishIfNotRoot(action: () -> Unit) {
    if (!isTaskRoot) {
        val mIntent = intent
        if (mIntent.isMainLauncherIntent) {
            finish()
            action()
        }
    }
}

fun AppCompatActivity.initInsets() {
    window.apply {
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            decorView.systemUiVisibility =
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        statusBarColor = ContextCompat.getColor(this@initInsets, R.color.colorPrimary)
        navigationBarColor = ContextCompat.getColor(this@initInsets, R.color.white_50)
    }
}

val Activity.rootView: View
    get() = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)

val Intent.isMainLauncherIntent: Boolean
    get() {
        val intentAction = action
        return hasCategory(Intent.CATEGORY_LAUNCHER) &&
                intentAction != null &&
                intentAction == Intent.ACTION_MAIN
    }
package com.test.testapp.base.activity

import androidx.annotation.LayoutRes
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.test.navigation.navigator.ActivityNavigator
import org.koin.android.ext.android.inject

abstract class BaseFlowActivity(
    @LayoutRes
    layoutId: Int
) : BaseActivity(layoutId) {

    private val navigationHolder: NavigatorHolder by inject()

    private val navigator: Navigator by lazy {
        createNavigator()
    }

    abstract val containerId: Int

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    open fun createNavigator(): Navigator = ActivityNavigator(containerId, this)
}
package com.test.testapp.base.fragment

import androidx.annotation.LayoutRes
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.test.navigation.navigator.FragmentNavigator
import com.test.testapp.base.navigation.CiceroneHolder
import org.koin.android.ext.android.inject

abstract class BaseFlowFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId) {

    private val ciceroneHolder: CiceroneHolder by inject()

    open val navigationHolder: NavigatorHolder
        get() = ciceroneHolder.getOrCreateHolder(navigatorKey)

    private val navigator: Navigator by lazy {
        createNavigator()
    }

    abstract val navigatorKey: String

    abstract val containerId: Int

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        if (FragmentDestroyChecker.isDestroying(this)) {
            ciceroneHolder.remove(navigatorKey)
        }
        super.onDestroy()
    }

    override fun backPressHandled(): Boolean {
        return if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStackImmediate()
            true
        } else {
            super.backPressHandled()
        }
    }

    override fun onShow() {
        childFragmentManager
            .fragments
            .forEach {
                (it as? BaseFragment)?.onShow()
            }
    }

    override fun onHide() {
        childFragmentManager
            .fragments
            .forEach {
                (it as? BaseFragment)?.onHide()
            }
    }

    open fun createNavigator(): Navigator = FragmentNavigator(containerId, this)
}
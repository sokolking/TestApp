package com.test.testapp.base.fragment.bottom

import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.test.navigation.navigator.FragmentNavigator
import com.test.testapp.base.fragment.FragmentDestroyChecker
import com.test.testapp.base.navigation.CiceroneHolder
import org.koin.android.ext.android.inject

abstract class BaseFlowBottomSheetFragment(
    layoutId: Int
) : BaseBottomSheetFragment(layoutId) {

    private val ciceroneHolder: CiceroneHolder by inject()

    private val navigator: Navigator by lazy {
        createNavigator()
    }

    abstract val navigatorKey: String

    open val navigationHolder: NavigatorHolder
        get() = ciceroneHolder.getOrCreateHolder(navigatorKey)

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

    open fun createNavigator(): Navigator = FragmentNavigator(0, this)
}
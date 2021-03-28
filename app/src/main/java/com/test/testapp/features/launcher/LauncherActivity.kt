package com.test.testapp.features.launcher

import android.os.Bundle
import com.test.testapp.R
import com.test.testapp.base.activity.BaseFlowActivity
import com.test.testapp.base.fragment.BaseFragment
import com.test.testapp.ext.finishIfNotRoot
import com.test.testapp.ext.initInsets
import org.koin.android.viewmodel.ext.android.viewModel

class LauncherActivity : BaseFlowActivity(R.layout.activity_launcher) {

    private val vm: LauncherVm by viewModel()

    override val containerId: Int = R.id.f_main_content

    override fun onCreate(savedInstanceState: Bundle?) {
        finishIfNotRoot {
            return
        }
        initInsets()
        super.onCreate(savedInstanceState)
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        vm.onActivityCreated(savedInstanceState)
    }

    override fun onBackPressed() {
        var currentFragment = supportFragmentManager.findFragmentById(containerId)
        if (currentFragment == null) {
            super.onBackPressed()
            return
        }
        if (currentFragment.childFragmentManager.fragments.isNotEmpty()) {
            currentFragment = currentFragment.childFragmentManager
                .fragments
                .lastOrNull { it.isVisible }
            if (currentFragment == null) {
                super.onBackPressed()
                return
            }
        }
        if (currentFragment is BaseFragment &&
            currentFragment.backPressHandled().not()
        ) {
            super.onBackPressed()
        }
    }
}
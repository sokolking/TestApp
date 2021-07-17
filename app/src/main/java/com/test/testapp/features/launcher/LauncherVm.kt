package com.test.testapp.features.launcher

import android.os.Bundle
import com.test.testapp.base.vm.BaseVm
import com.test.testapp.features.main.MainRoute
import com.test.testapp.features.main_flow.MainFlowRoute

class LauncherVm : BaseVm<LauncherState>() {

    override val state: LauncherState = LauncherState()

    fun onActivityCreated(savedState: Bundle?) {
        if (savedState == null) {
            router.newRootScreen(MainFlowRoute.withDefaultAnim())
        }
    }

}
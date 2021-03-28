package com.test.testapp.features.launcher

import android.os.Bundle
import com.test.testapp.base.vm.BaseVm
import com.test.testapp.features.other.OtherRoute

class LauncherVm : BaseVm<LauncherState>() {

    override val state: LauncherState = LauncherState()

    fun onActivityCreated(savedState: Bundle?) {
        if (savedState == null) {
            router.newRootScreen(OtherRoute.withDefaultAnim())
        }
    }

}
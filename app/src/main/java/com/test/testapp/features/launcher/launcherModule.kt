package com.test.testapp.features.launcher

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val launcherModule = module {
    viewModel { LauncherVm() }
}
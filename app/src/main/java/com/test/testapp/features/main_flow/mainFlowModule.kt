package com.test.testapp.features.main_flow

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainFlowModule = module {
    viewModel { (navigatorKey: String) -> MainFlowVm(navigatorKey) }
}
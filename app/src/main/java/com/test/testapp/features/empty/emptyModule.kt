package com.test.testapp.features.empty

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val emptyModule = module {
    viewModel { EmptyVm() }
}
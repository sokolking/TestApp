package com.test.testapp.features.other

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val otherModule = module {
    viewModel { OtherVm(get()) }
}
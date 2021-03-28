package com.test.testapp.features.search

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { (lowerBound: Int, upperBound: Int) -> SearchVm(lowerBound, upperBound, get()) }
}
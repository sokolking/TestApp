package com.test.testapp.base.vm

import com.test.testapp.base.properties.ObjectBindableProperty

open class BaseVmState {

    val loading = ObjectBindableProperty<LoadingState>(LoadingState.None)
}

sealed class LoadingState {

    object Loading : LoadingState()
    object None : LoadingState()
    object Transparent : LoadingState()
}
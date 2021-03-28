package com.test.testapp.features.main

import com.test.testapp.base.properties.ObjectBindableProperty
import com.test.testapp.base.vm.BaseVmState

class MainState : BaseVmState() {
    val showLowerError = ObjectBindableProperty<Pair<Boolean, Int>>()
    val showUpperError = ObjectBindableProperty<Pair<Boolean, Int>>()
}
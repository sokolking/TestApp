package com.test.testapp.features.main

import com.test.domain.api.comments.ExplorerData
import com.test.testapp.base.properties.ListBindableProperty
import com.test.testapp.base.vm.BaseVmState

class MainState : BaseVmState() {
    val explorers = ListBindableProperty<ExplorerData>()
}
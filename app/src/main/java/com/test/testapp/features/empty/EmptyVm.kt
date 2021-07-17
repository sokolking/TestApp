package com.test.testapp.features.empty

import com.test.testapp.base.vm.BaseVm

class EmptyVm : BaseVm<EmptyState>() {

    override val state: EmptyState = EmptyState()

}
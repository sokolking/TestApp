package com.test.testapp.features.main_flow

import com.test.testapp.base.properties.IntBindableProperty
import com.test.testapp.base.vm.BaseVmState

class MainFlowState : BaseVmState() {

    val bottomNavigationItems = IntBindableProperty()
}

/**
 * events that child sent to parent
 */
sealed class MainFlowChildEvent {

    data class ConfirmOpenAnotherPage(val itemId: Int) : MainFlowChildEvent()
}

/**
 * events that parent sent to child
 */
sealed class MainFlowParentEvent {

    data class TryOpenAnotherPage(val itemId: Int) : MainFlowParentEvent()
}

/**
 * events that parent sent to itself
 */
sealed class MainFlowEvent {

    data class SelectBottomItem(val itemId: Int) : MainFlowEvent()
}
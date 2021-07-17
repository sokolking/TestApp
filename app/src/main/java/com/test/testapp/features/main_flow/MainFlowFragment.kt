package com.test.testapp.features.main_flow

import android.os.Bundle
import com.test.base.ext.clearInsetsListener
import com.test.testapp.R
import com.test.testapp.base.fragment.BaseFlowFragment
import com.test.testapp.base.properties.bind
import com.test.testapp.base.properties.observeEvents
import com.test.testapp.ext.hideKeyboard
import com.test.testapp.ext.initDefaultInsetsFragment
import kotlinx.coroutines.flow.Flow
import org.koin.android.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_main_flow.*
import org.koin.core.parameter.parametersOf

class MainFlowFragment : BaseFlowFragment(R.layout.fragment_main_flow),
    ParentEventsProvider<MainFlowParentEvent, MainFlowChildEvent> {

    private val vm: MainFlowVm by viewModel {
        parametersOf(MainFlowRoute.fetchArgNavigatorKey(requireArguments()))
    }

    override fun getParentEventsChannel(): Flow<MainFlowParentEvent> = vm.parentEventsFlow

    override val navigatorKey: String
        get() = MainFlowRoute.fetchArgNavigatorKey(requireArguments())

    override val containerId: Int = R.id.f_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onRestoreState(savedInstanceState)
    }

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        vm.onFragmentCreated(savedInstanceState, recreated)
        initInsets()
        initBottomNav()
        vm.eventsFlow
            .observeEvents(this) {
                if (it is MainFlowEvent.SelectBottomItem) {
                    v_bottom_navigation.selectedItemId = it.itemId
                }
            }
        bindProperties()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vm.onSaveState(outState)
    }

    override fun onDestroyView() {
        v_root.clearInsetsListener()
        super.onDestroyView()
    }

    override fun onHide() {
        super.onHide()
        v_root.clearInsetsListener()
    }

    override fun onShow() {
        super.onShow()
        initInsets()
    }

    override fun onChildEvent(event: MainFlowChildEvent) {
        when (event) {
            is MainFlowChildEvent.ConfirmOpenAnotherPage -> vm.onLeaveMyCompanyConfirmed(event.itemId)
        }
    }

    private fun bindProperties() {
        with(vm.state) {
            bind(bottomNavigationItems) {
                v_bottom_navigation.menu.clear()
                v_bottom_navigation.inflateMenu(it)
            }
        }
    }

    private fun initBottomNav() {
        v_bottom_navigation.setOnNavigationItemSelectedListener {
            hideKeyboard()
            vm.onBottomMenuSelected(it.itemId)
        }
    }

    private fun initInsets() {
        initDefaultInsetsFragment(v_root)
    }
}
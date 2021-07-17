package com.test.testapp.features.main_flow

import android.os.Bundle
import com.test.navigation.route.FragmentRoute
import com.test.navigation.router.AppRouter
import com.test.testapp.R
import com.test.testapp.base.vm.BaseVm
import com.test.testapp.utils.MainTabsProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val KEY_CURRENT_ITEM = "key_current_item"

class MainFlowVm(private val navigatorKey: String) : BaseVm<MainFlowState>() {

    private var currentItemId = -1

    private val parentEventsChannel = Channel<MainFlowParentEvent>(Channel.BUFFERED)
    private val eventsChannel = Channel<MainFlowEvent>(Channel.BUFFERED)
    private val fragmentRouter: AppRouter
        get() = ciceroneHolder.getOrCreateRouter(navigatorKey)

    val parentEventsFlow: Flow<MainFlowParentEvent>
        get() = parentEventsChannel.receiveAsFlow()

    val eventsFlow: Flow<MainFlowEvent>
        get() = eventsChannel.receiveAsFlow()

    override val state: MainFlowState = MainFlowState()

    fun onFragmentCreated(
        savedState: Bundle?,
        recreated: Boolean,
    ) {
        if (savedState == null && recreated.not()) {
            state.bottomNavigationItems.post(R.menu.menu_main_flow)
            currentItemId = R.id.action_home
            fragmentRouter.newRootScreen(MainTabsProvider.homeScreen)
        }
    }

    fun onBottomMenuSelected(menuId: Int): Boolean {
        if (currentItemId == menuId) {
            return true
        }
        return when (menuId) {
            R.id.action_home -> checkLeavingMyCompanyScreen(
                menuId,
                MainTabsProvider.homeScreen
            )
            R.id.action_explorer -> checkLeavingMyCompanyScreen(
                menuId,
                MainTabsProvider.explorerScreen
            )
            R.id.action_net_of_nets -> checkLeavingMyCompanyScreen(
                menuId,
                MainTabsProvider.netOfNetsScreen
            )
            R.id.action_activity -> checkLeavingMyCompanyScreen(
                menuId,
                MainTabsProvider.activityScreen
            )
            R.id.action_post_a_job -> checkLeavingMyCompanyScreen(
                menuId,
                MainTabsProvider.postJobScreen
            )
            else -> true
        }
    }

    fun onLeaveMyCompanyConfirmed(menuId: Int) {
        currentItemId = -1
        onBottomMenuSelected(menuId)
        sendEvent(MainFlowEvent.SelectBottomItem(menuId))
    }

    fun onSaveState(out: Bundle) {
        out.putInt(KEY_CURRENT_ITEM, currentItemId)
    }

    fun onRestoreState(inState: Bundle?) {
        inState ?: return
        currentItemId = inState.getInt(KEY_CURRENT_ITEM, -1)
    }

    private fun checkLeavingMyCompanyScreen(menuId: Int, newRoute: FragmentRoute): Boolean {
        openTab(menuId, newRoute)
        return true
    }

    private fun openTab(menuId: Int, newTab: FragmentRoute) {
        currentItemId = menuId
        fragmentRouter.replaceTab(newTab)
    }

    private fun sendParentEvent(event: MainFlowParentEvent) {
        launch {
            parentEventsChannel.send(event)
        }
    }

    private fun sendEvent(event: MainFlowEvent) {
        launch {
            eventsChannel.send(event)
        }
    }
}
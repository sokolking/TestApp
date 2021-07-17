package com.test.testapp.utils

import com.test.navigation.route.FragmentRoute
import com.test.testapp.features.empty.EmptyRoute
import com.test.testapp.features.main.MainRoute
import org.koin.core.KoinComponent

object MainTabsProvider : KoinComponent {

    val homeScreen: FragmentRoute = EmptyRoute

    val explorerScreen: FragmentRoute = MainRoute

    val netOfNetsScreen: FragmentRoute = EmptyRoute

    val activityScreen: FragmentRoute = EmptyRoute

    val postJobScreen: FragmentRoute = EmptyRoute
}
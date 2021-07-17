package com.test.testapp.features.main_flow

import android.os.Bundle
import com.test.navigation.route.FragmentRoute
import com.test.testapp.features.main_flow.MainFlowRoute.ARG_NAVIGATOR_KEY
import java.util.*

object MainFlowRoute : FragmentRoute({
    MainFlowFragment().apply {
        arguments = Bundle().apply {
            putString(ARG_NAVIGATOR_KEY, UUID.randomUUID().toString())
        }
    }
}) {

    private const val ARG_NAVIGATOR_KEY = "arg_navigator_key"

    fun fetchArgNavigatorKey(args: Bundle): String =
        args.getString(ARG_NAVIGATOR_KEY, "temp")
}
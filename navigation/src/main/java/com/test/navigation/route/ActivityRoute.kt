package com.test.navigation.route

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen

open class ActivityRoute(
    private val creator: () -> Intent
) : ActivityScreen(
    intentCreator = { creator() }
) {

    var animation: TransitionAnimation? = null

    fun withDefaultAnim(): ActivityRoute = this.apply {
        animation = TransitionAnimation()
    }

    fun withCustomAnim(transition: TransitionAnimation): ActivityRoute = this.apply {
        animation = transition
    }
}
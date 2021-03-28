package com.test.navigation.route

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class FragmentRoute(
    private val creator: () -> Fragment
) : FragmentScreen(fragmentCreator = { creator() }) {

    var animation: TransitionAnimation? = null

    fun withDefaultAnim(): FragmentRoute = this.apply {
        animation = TransitionAnimation()
    }

    fun withCustomAnim(transition: TransitionAnimation): FragmentRoute = this.apply {
        animation = transition
    }
}
package com.test.navigation.commands

import com.github.terrakok.cicerone.Command
import com.test.navigation.route.FragmentRoute

class AddScreenCommand(
    val screen: FragmentRoute,
    val hideCurrent: Boolean
) : Command
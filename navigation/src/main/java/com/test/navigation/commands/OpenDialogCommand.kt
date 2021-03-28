package com.test.navigation.commands

import com.github.terrakok.cicerone.Command
import com.test.navigation.route.FragmentRoute

class OpenDialogCommand(
    val screen: FragmentRoute,
    val replaceCurrent: Boolean = false
) : Command
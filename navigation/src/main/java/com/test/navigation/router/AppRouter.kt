package com.test.navigation.router

import android.os.Bundle
import com.github.terrakok.cicerone.Router
import com.test.navigation.commands.AddScreenCommand
import com.test.navigation.commands.ExitWithFragmentResult
import com.test.navigation.commands.OpenDialogCommand
import com.test.navigation.commands.ReplaceTabCommand
import com.test.navigation.route.FragmentRoute

class AppRouter : Router() {

    fun openDialog(screen: FragmentRoute, replaceCurrent: Boolean = false) {
        executeCommands(OpenDialogCommand(screen, replaceCurrent))
    }

    fun addScreen(screen: FragmentRoute, hideCurrent: Boolean = true) {
        executeCommands(AddScreenCommand(screen, hideCurrent))
    }

    fun exitWithFragmentResult(requestCode: String, data: Bundle = Bundle.EMPTY) {
        executeCommands(ExitWithFragmentResult(requestCode, data))
    }

    fun replaceTab(screen: FragmentRoute) {
        executeCommands(ReplaceTabCommand(screen))
    }
}
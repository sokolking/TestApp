package com.test.navigation.navigator

import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.AppScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.github.terrakok.cicerone.androidx.TransactionInfo
import com.test.navigation.commands.AddScreenCommand
import com.test.navigation.commands.ExitWithFragmentResult
import com.test.navigation.commands.OpenDialogCommand
import com.test.navigation.commands.ReplaceTabCommand
import com.test.navigation.route.FragmentRoute

abstract class BaseNavigator(
    private val fragmentManager: FragmentManager,
    protected val containerId: Int
) : Navigator {

    private val fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
    private val localStackCopy = mutableListOf<TransactionInfo>()

    override fun applyCommands(commands: Array<out Command>) {
        fragmentManager.executePendingTransactions()

        //copy stack before apply commands
        copyStackToLocal()

        for (command in commands) {
            try {
                applyCommand(command)
            } catch (e: RuntimeException) {
                errorOnApplyCommand(command, e)
            }
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected open fun applyCommand(command: Command) {
        when (command) {
            is Forward -> forward(command)
            is Replace -> replace(command)
            is BackTo -> backTo(command)
            is Back -> back()
            is OpenDialogCommand -> openDialog(command)
            is AddScreenCommand -> addScreen(command)
            is ExitWithFragmentResult -> exitWithFragmentResult(command)
            is ReplaceTabCommand -> replaceTab(command)
        }
    }

    protected open fun forward(command: Forward) {
        when (val screen = command.screen as AppScreen) {
            is ActivityScreen -> {
                checkAndStartActivity(screen)
            }
            is FragmentScreen -> {
                val type = if (command.clearContainer) TransactionInfo.Type.REPLACE else TransactionInfo.Type.ADD
                commitNewFragmentScreen(screen, type, true)
            }
        }
    }

    protected open fun replace(command: Replace) {
        when (val screen = command.screen as AppScreen) {
            is ActivityScreen -> {
                checkAndStartActivity(screen)
                activityBack()
            }
            is FragmentScreen -> {
                if (localStackCopy.isNotEmpty()) {
                    fragmentManager.popBackStack()
                    val removed = localStackCopy.removeAt(localStackCopy.lastIndex)
                    commitNewFragmentScreen(screen, removed.type, true)
                } else {
                    commitNewFragmentScreen(screen, TransactionInfo.Type.REPLACE, false)
                }
            }
        }
    }

    protected open fun back() {
        if (localStackCopy.isNotEmpty()) {
            fragmentManager.popBackStack()
            localStackCopy.removeAt(localStackCopy.lastIndex)
        } else {
            activityBack()
        }
    }

    protected open fun openDialog(command: OpenDialogCommand) {
        val tag = command.screen.screenKey
        val fragments = fragmentManager.fragments
        val current = fragments.find { it.tag == tag }
        if (current != null) {
            if (command.replaceCurrent) {
                (fragments as? DialogFragment)?.dismiss()
            } else {
                return
            }
        }
        val dialog = command.screen.createFragment(fragmentFactory) as? DialogFragment
        dialog?.show(fragmentManager, tag)
    }

    protected open fun activityBack() {
    }

    protected open fun exitWithFragmentResult(command: ExitWithFragmentResult) {
    }

    private fun replaceTab(command: ReplaceTabCommand) {
        val screen = command.screen

        //find current fragment
        var currentFragment: Fragment? = null
        val fragments = fragmentManager.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }

        //find new fragment
        val newFragment = fragmentManager.findFragmentByTag(screen.screenKey)
        //currently visible fragment is new fragment
        if (currentFragment != null
            && newFragment != null
            && currentFragment === newFragment
        ) {
            return
        }

        val transaction = fragmentManager.beginTransaction()

        if (newFragment == null) {
            transaction.add(containerId, screen.createFragment(fragmentFactory), screen.screenKey)
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
        }

        transaction.commit()
    }


    protected open fun commitNewFragmentScreen(
        screen: FragmentScreen,
        type: TransactionInfo.Type,
        addToBackStack: Boolean
    ) {
        val fragment = screen.createFragment(fragmentFactory)
        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
        if (screen is FragmentRoute) {
            screen.animation?.let {
                transaction
                    .setCustomAnimations(
                        it.enter,
                        it.exit,
                        it.popEnter,
                        it.popExit
                    )
            }
        }
        setupFragmentTransaction(
            transaction,
            fragmentManager.findFragmentById(containerId),
            fragment
        )
        when (type) {
            TransactionInfo.Type.ADD -> transaction.add(containerId, fragment, screen.screenKey)
            TransactionInfo.Type.REPLACE -> transaction.replace(containerId, fragment, screen.screenKey)
        }
        if (addToBackStack) {
            val transactionInfo = TransactionInfo(screen.screenKey, type)
            transaction.addToBackStack(transactionInfo.toString())
            localStackCopy.add(transactionInfo)
        }
        transaction.commit()
    }

    /**
     * Performs [BackTo] command transition
     */
    protected open fun backTo(command: BackTo) {
        val screen = command.screen
        if (screen == null) {
            backToRoot()
        } else {
            val screenKey = screen.screenKey
            val index = localStackCopy.indexOfFirst { it.screenKey == screenKey }
            if (index != -1) {
                val forRemove = localStackCopy.subList(index, localStackCopy.size)
                fragmentManager.popBackStack(forRemove.first().toString(), 0)
                forRemove.clear()
            } else {
                backToUnexisting(screen as AppScreen)
            }
        }
    }

    /**
     * Override this method to setup fragment transaction [FragmentTransaction].
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param fragmentTransaction fragment transaction
     * @param currentFragment     current fragment in container
     *                            (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     */
    protected open fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        // Do nothing by default
    }

    /**
     * Called when there is no activity to open `screenKey`.
     *
     * @param screen         screen
     * @param activityIntent intent passed to start Activity for the `screenKey`
     */
    protected open fun unexistingActivity(
        screen: ActivityScreen,
        activityIntent: Intent
    ) {
        // Do nothing by default
    }

    /**
     * Called when we tried to fragmentBack to some specific screen (via [BackTo] command),
     * but didn't found it.
     *
     * @param screen screen
     */
    protected open fun backToUnexisting(screen: AppScreen) {
        backToRoot()
    }

    /**
     * Override this method if you want to handle apply command error.
     *
     * @param command command
     * @param error   error
     */
    protected open fun errorOnApplyCommand(
        command: Command,
        error: RuntimeException
    ) {
        throw error
    }

    protected open fun checkAndStartActivity(screen: ActivityScreen) {
    }

    private fun addScreen(command: AddScreenCommand) {
        val screen = command.screen
        val newFragment = screen.createFragment(fragmentFactory)
        val fragmentTransaction = fragmentManager.beginTransaction()
        command.screen.animation?.let {
            fragmentTransaction
                .setCustomAnimations(
                    it.enter,
                    it.exit,
                    it.popEnter,
                    it.popExit
                )
        }
        val transactionInfo = TransactionInfo(screen.screenKey, TransactionInfo.Type.ADD)

        val currentFragment = fragmentManager.fragments.firstOrNull { it.isVisible }
        if (currentFragment != null && command.hideCurrent) {
            fragmentTransaction
                .hide(currentFragment)
        }

        fragmentTransaction
            .add(containerId, newFragment, screen.screenKey)
            .addToBackStack(transactionInfo.toString())
            .commit()
        localStackCopy.add(transactionInfo)
    }

    private fun copyStackToLocal() {
        localStackCopy.clear()
        for (i in 0 until fragmentManager.backStackEntryCount) {
            val str = fragmentManager.getBackStackEntryAt(i).name
            if (str != null) {
                localStackCopy.add(TransactionInfo.fromString(str))
            }
        }
    }

    private fun backToRoot() {
        localStackCopy.clear()
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
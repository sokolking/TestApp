package com.test.navigation.navigator

import android.content.ActivityNotFoundException
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.test.navigation.commands.ExitWithFragmentResult

class FragmentNavigator(
    containerId: Int,
    private val fragment: Fragment,
) : BaseNavigator(
    fragment.childFragmentManager,
    containerId
) {

    override fun checkAndStartActivity(screen: ActivityScreen) {
        val activity = fragment.activity ?: return
        // Check if we can start activity
        val activityIntent = screen.createIntent(activity)
        try {
            activity.startActivity(activityIntent, screen.startActivityOptions)
        } catch (e: ActivityNotFoundException) {
            unexistingActivity(screen, activityIntent)
        }
    }

    override fun activityBack() {
        fragment.activity?.finish()
    }

    override fun exitWithFragmentResult(command: ExitWithFragmentResult) {
        fragment
            .parentFragmentManager
            .setFragmentResult(
                command.requestCode,
                command.data
            )
        back()
    }

    override fun back() {
        if (fragment is DialogFragment) {
            fragment.dismiss()
            return
        }
        super.back()
    }
}
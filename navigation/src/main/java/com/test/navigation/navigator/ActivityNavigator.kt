package com.test.navigation.navigator

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.test.navigation.commands.ExitWithFragmentResult

class ActivityNavigator(
    containerId: Int,
    private val activity: AppCompatActivity,
) : BaseNavigator(
    activity.supportFragmentManager,
    containerId
) {

    override fun checkAndStartActivity(screen: ActivityScreen) {
        // Check if we can start activity
        val activityIntent = screen.createIntent(activity)
        try {
            activity.startActivity(activityIntent, screen.startActivityOptions)
        } catch (e: ActivityNotFoundException) {
            unexistingActivity(screen, activityIntent)
        }
    }

    override fun activityBack() {
        activity.finish()
    }

    override fun exitWithFragmentResult(command: ExitWithFragmentResult) {
        val fragment =
            activity
                .supportFragmentManager
                .fragments
                .firstOrNull { it.isVisible }
        fragment
            ?.parentFragmentManager
            ?.setFragmentResult(
                command.requestCode,
                command.data
            )
        back()
    }
}
package com.test.base.message

import android.app.Activity
import com.test.base.activity.EmptyActivityLifecycleCallbacks

class MessageControllerActivityCallback(
    private val messageController: MessageController
) : EmptyActivityLifecycleCallbacks() {

    override fun onActivityPaused(activity: Activity) {
        messageController.cancelAll()
    }
}
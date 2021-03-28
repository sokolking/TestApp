package com.test.testapp

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.test.base.activity.CurrentActivityHolderActivityCallback
import com.test.base.message.MessageControllerActivityCallback
import com.test.testapp.di.koinModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TestApp : MultiDexApplication() {

    private val activityHolderCallback: CurrentActivityHolderActivityCallback by inject()
    private val messageControllerCallback: MessageControllerActivityCallback by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initCallbacks()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun initCallbacks() {
        registerActivityLifecycleCallbacks(activityHolderCallback)
        registerActivityLifecycleCallbacks(messageControllerCallback)
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.ERROR)
            }
            androidContext(applicationContext)
            modules(koinModules)
        }
    }

}
package com.test.testapp.di.core

import com.test.base.activity.CurrentActivityHolder
import com.test.base.activity.CurrentActivityHolderActivityCallback
import com.test.base.coroutines.DefaultDispatchersProvider
import com.test.base.coroutines.DispatchersProvider
import com.test.base.ext.IntentHelper
import com.test.base.message.DefaultMessageController
import com.test.base.message.MessageController
import com.test.base.message.MessageControllerActivityCallback
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {

    single<DispatchersProvider> { DefaultDispatchersProvider() }

    single { CurrentActivityHolder() }
    single { CurrentActivityHolderActivityCallback(get()) }

    single<MessageController> { DefaultMessageController(get()) }
    single { MessageControllerActivityCallback(get()) }

    single { IntentHelper(androidContext()) }
}
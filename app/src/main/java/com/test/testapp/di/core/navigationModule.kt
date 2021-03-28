package com.test.testapp.di.core

import com.github.terrakok.cicerone.Cicerone
import com.test.navigation.router.AppRouter
import com.test.testapp.base.navigation.CiceroneHolder
import org.koin.dsl.module

val navigationModule = module {

    single { CiceroneHolder() }

    single { Cicerone.create(AppRouter()) }
    single {
        val cicerone: Cicerone<AppRouter> = get()
        cicerone.router
    }
    single {
        val cicerone: Cicerone<AppRouter> = get()
        cicerone.getNavigatorHolder()
    }
}
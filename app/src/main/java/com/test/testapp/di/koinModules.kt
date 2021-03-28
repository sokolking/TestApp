package com.test.testapp.di

import com.test.testapp.di.core.applicationModule
import com.test.testapp.di.core.filesModule
import com.test.testapp.di.core.navigationModule
import com.test.testapp.features.launcher.launcherModule
import com.test.testapp.features.other.otherModule
import org.koin.core.module.Module

private val featureModules: List<Module> = listOf(
    launcherModule,
    otherModule
)

private val coreModules: List<Module> = listOf(
    applicationModule,
    navigationModule,
    filesModule
)

private val interactorsModules: List<Module> = listOf(

)

val koinModules: List<Module> = coreModules + interactorsModules + featureModules
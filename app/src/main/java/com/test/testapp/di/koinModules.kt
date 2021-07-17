package com.test.testapp.di

import com.test.testapp.di.core.applicationModule
import com.test.testapp.di.core.filesModule
import com.test.testapp.di.core.navigationModule
import com.test.testapp.di.interactors.commentInteractorModule
import com.test.testapp.features.empty.emptyModule
import com.test.testapp.features.launcher.launcherModule
import com.test.testapp.features.main.mainModule
import com.test.testapp.features.main_flow.mainFlowModule
import com.test.testapp.features.search.searchModule
import org.koin.core.module.Module

private val featureModules: List<Module> = listOf(
    launcherModule,
    mainFlowModule,
    emptyModule,
    mainModule,
    searchModule
)

private val coreModules: List<Module> = listOf(
    applicationModule,
    navigationModule,
    filesModule
)

private val interactorsModules: List<Module> = listOf(
    commentInteractorModule
)

val koinModules: List<Module> = coreModules + interactorsModules + featureModules
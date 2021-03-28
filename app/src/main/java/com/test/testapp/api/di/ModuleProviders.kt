package com.test.testapp.api.di

import com.test.api.JsonPlaceHolderldApi
import com.test.testapp.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ModuleProviders : BaseRouticApiModuleProviders() {
    @Provides
    @Singleton
    fun api(): JsonPlaceHolderldApi {
        return JsonPlaceHolderldApi.create(BuildConfig.API, client())
    }
}
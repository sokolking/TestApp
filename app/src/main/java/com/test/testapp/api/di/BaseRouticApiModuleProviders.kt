package com.test.testapp.api.di

import com.test.base.log.TestAppLog
import com.test.testapp.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class BaseRouticApiModuleProviders {

    @Provides
    @Singleton
    fun client(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor { message -> TestAppLog.log(message) }
                        .apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                        .run(::addNetworkInterceptor)
                }
            }
            .build()
    }
}
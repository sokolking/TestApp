package com.test.testapp.di.core

import com.test.base.files.FileHelper
import com.test.base.files.ImageNormalizer
import com.test.base.files.PictureProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val filesModule = module {

    single { FileHelper(androidContext()) }

    single { PictureProvider(androidContext(), get(), get()) }
    single { ImageNormalizer(get(), get()) }
}
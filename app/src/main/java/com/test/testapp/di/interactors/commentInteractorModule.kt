package com.test.testapp.di.interactors

import com.test.i_comment.CommentInteractor
import com.test.i_comment.repository.CommentRepository
import com.test.testapp.api.di.ModuleProviders
import org.koin.dsl.module

val commentInteractorModule = module {

    single { CommentRepository(ModuleProviders.api()) }
    single { CommentInteractor(get()) }
}
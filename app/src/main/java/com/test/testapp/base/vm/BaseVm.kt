package com.test.testapp.base.vm

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.test.base.coroutines.DispatchersProvider
import com.test.base.coroutines.RequestResult
import com.test.base.log.TestAppLog
import com.test.base.message.MessageController
import com.test.navigation.router.AppRouter
import com.test.testapp.R
import com.test.testapp.base.navigation.CiceroneHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseVm<S : BaseVmState> :
    ViewModel(),
    KoinComponent,
    CoroutineScope {

    private val parentJob = SupervisorJob()

    protected val ciceroneHolder: CiceroneHolder by inject()
    protected val router: AppRouter by inject()

    protected val messageController: MessageController by inject()

    protected val dispatchers: DispatchersProvider by inject()

    override val coroutineContext: CoroutineContext = dispatchers.default() + parentJob

    abstract val state: S

    override fun onCleared() {
        parentJob.cancelChildren()
    }

    @MainThread
    protected open fun onError(error: Throwable) {
        TestAppLog.log("error: ${error.message}")
        state.loading.post(LoadingState.None)
        showErrorMessage(error)
    }

    protected suspend fun runOnUi(block: suspend () -> Unit) =
        withContext(this.dispatchers.main()) {
            block()
        }

    protected suspend fun <T> RequestResult<T>.handleResult(
        block: suspend (T) -> Unit
    ) = runOnUi {
        when (val result = this@handleResult) {
            is RequestResult.Success -> block(result.data)
            is RequestResult.Error -> onError(result.error)
        }
    }

    protected suspend fun <T> RequestResult<T>.handleResultWithError(
        resultBlock: suspend (T) -> Unit,
        errorBlock: suspend (Throwable) -> Unit
    ) = runOnUi {
        when (val result = this@handleResultWithError) {
            is RequestResult.Success -> resultBlock(result.data)
            is RequestResult.Error -> errorBlock(result.error)
        }
    }

    @MainThread
    protected open fun showErrorMessage(error: Throwable) {
        when (error) {
            else -> messageController.showToast(R.string.error_common)
        }
    }

    open fun exit() {
        router.exit()
    }
}
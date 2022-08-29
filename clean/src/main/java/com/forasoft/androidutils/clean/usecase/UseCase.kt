package com.forasoft.androidutils.clean.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class UseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    @Suppress("TooGenericExceptionCaught")
    suspend operator fun invoke(params: P): Result<R> {
        return try {
            withContext(dispatcher) {
                execute(params).let {
                    Result.success(it)
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Exception occurred while executing ${this.javaClass.simpleName} " +
                    "with parameters $params")
            Result.failure(e)
        }
    }

    protected abstract suspend fun execute(params: P): R

}

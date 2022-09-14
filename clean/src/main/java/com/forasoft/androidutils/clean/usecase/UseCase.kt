package com.forasoft.androidutils.clean.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic).
 *
 * @param P type of operation parameters.
 * @param R type of operation expected result.
 * @see [FlowUseCase]
 * @see [SimpleUseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
abstract class UseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    /**
     * Invokes the operation with given parameters and returns its encapsulated result
     * if the invocation was successful, catching any thrown [Exception] and
     * encapsulating it as a failure.
     *
     * @param params operation parameters.
     * @return [Result] that encapsulates the successful result of the operation or caught
     * [Exception].
     */
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

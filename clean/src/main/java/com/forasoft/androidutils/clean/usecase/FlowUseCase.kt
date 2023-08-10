package com.forasoft.androidutils.clean.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic).
 *
 * Override [shouldRetry] method to specify whether to retry [Flow] collection
 * when an exception occurs. Default implementation returns `false`.
 *
 * @param P type of operation parameters.
 * @param R type of operation expected result.
 * @see [UseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
public abstract class FlowUseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    private val className = if (Timber.treeCount != 0) this.javaClass.simpleName else TAG

    /**
     * Invokes the operation with given parameters and returns [Flow] of encapsulated result
     * if it is successful, catching any thrown [Exception] and encapsulating it as a failure.
     *
     * @param params operation parameters.
     * @return [Flow] of [Result] that encapsulates the successful result of the operation
     * or caught [Exception].
     */
    public operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .map { Result.success(it) }
        .retryWhen { e, attempt ->
            Timber.tag(className).e(e, "Exception occurred while executing $className with parameters $params")
            emit(Result.failure(e))
            shouldRetry(e, attempt)
        }
        .catch { e ->
            Timber.tag(className).e(e, "Exception occurred while executing $className with parameters $params")
            emit(Result.failure(e))
        }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>

    /**
     * Specifies whether to retry [Flow] collection when an exception occurs.
     * Default implementation returns `false`.
     */
    public open suspend fun shouldRetry(exception: Throwable, attempt: Long): Boolean = false

    public companion object {
        private const val TAG = "FlowUseCase"
    }

}

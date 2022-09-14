@file:Suppress("unused")

package com.forasoft.androidutils.clean.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic).
 *
 * @param P type of operation parameters.
 * @param R type of operation expected result.
 * @see [UseCase]
 * @see [SimpleUseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
abstract class FlowUseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    /**
     * Invokes the operation with given parameters and returns [Flow] of encapsulated result
     * if it is successful, catching any thrown [Exception] and encapsulating it as a failure.
     *
     * @param params operation parameters.
     * @return [Flow] of [Result] that encapsulates the successful result of the operation
     * or caught [Exception].
     */
    operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .catch { e ->
            Timber.e(e, "Exception occurred while executing ${this.javaClass.simpleName} " +
                    "with parameters $params")
            emit(Result.failure(e))
        }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<Result<R>>

}

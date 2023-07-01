package com.forasoft.androidutils.clean.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic).
 *
 * @param P type of operation parameters.
 * @param R type of operation expected result.
 * @see [UseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
abstract class FlowUseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    private val className = if (Timber.treeCount != 0) this.javaClass.simpleName else TAG

    /**
     * Invokes the operation with given parameters and returns [Flow] of encapsulated result
     * if it is successful, catching any thrown [Exception] and encapsulating it as a failure.
     *
     * @param params operation parameters.
     * @return [Flow] of [Result] that encapsulates the successful result of the operation
     * or caught [Exception].
     */
    operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .retryWhen { e, _ ->
            Timber.tag(className).e(e, "Exception occurred while executing $className with parameters $params")
            emit(Result.failure(e))
            true
        }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<Result<R>>

    companion object {
        private const val TAG = "FlowUseCase"
    }

}

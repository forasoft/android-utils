package com.forasoft.androidutils.clean.usecase.parameterless

import com.forasoft.androidutils.clean.usecase.FlowUseCase
import com.forasoft.androidutils.clean.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic). Parameterless version of [FlowUseCase].
 *
 * Override [shouldRetry] method to specify whether to retry flow collection
 * when an exception occurs. Default implementation returns `false`.
 *
 * @param R type of operation expected result.
 * @see [UseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
public abstract class FlowUseCase<out R>(dispatcher: CoroutineDispatcher) :
    FlowUseCase<Unit, R>(dispatcher) {

    /**
     * Invokes the operation and returns [Flow] of encapsulated result
     * if it is successful, catching any thrown [Exception] and encapsulating it as a failure.
     *
     * @return [Flow] of [Result] that encapsulates the successful result of the operation
     * or caught [Exception].
     */
    public operator fun invoke(): Flow<Result<R>> = invoke(Unit)

}

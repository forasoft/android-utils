package com.forasoft.androidutils.clean.usecase.parameterless

import com.forasoft.androidutils.clean.usecase.FlowUseCase
import com.forasoft.androidutils.clean.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic). Parameterless version of [UseCase].
 *
 * @param R type of operation expected result.
 * @see [FlowUseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
public abstract class UseCase<out R>(dispatcher: CoroutineDispatcher) : UseCase<Unit, R>(dispatcher) {

    /**
     * Invokes the operation and returns its encapsulated result
     * if the invocation was successful, catching any thrown [Exception] and
     * encapsulating it as a failure.
     *
     * @return [Result] that encapsulates the successful result of the operation or caught
     * [Exception].
     */
    public suspend operator fun invoke(): Result<R> = invoke(Unit)

}

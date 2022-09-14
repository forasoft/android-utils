package com.forasoft.androidutils.clean.usecase

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic).
 *
 * **Note**: this UseCase implementation does not catch any exceptions and does not
 * allow to invoke `suspend` functions. Consider using [UseCase] or [FlowUseCase].
 *
 * @param P type of operation parameters.
 * @param R type of operation expected result.
 * @see [UseCase]
 * @see [SimpleUseCase]
 */
interface SimpleUseCase<in P, out R> {

    /**
     * Invokes the operation with given parameters and returns its result.
     *
     * @param params operation parameters.
     * @return operation result.
     */
    operator fun invoke(params: P): R
}

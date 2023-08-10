package com.forasoft.androidutils.clean.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

/**
 * Implementation of UseCase Clean Architecture pattern.
 *
 * Encapsulates an operation (usually business logic).
 *
 * @param P type of operation parameters.
 * @param R type of operation expected result.
 * @see [FlowUseCase]
 * @property dispatcher [CoroutineDispatcher] to run the operation on.
 */
public abstract class UseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    private val className = if (Timber.treeCount != 0) this.javaClass.simpleName else TAG

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
    public suspend operator fun invoke(params: P): Result<R> {
        return try {
            val result: Result<R>
            val executionDuration = measureTimeMillis {
                withContext(dispatcher) {
                    execute(params).let {
                        result = Result.success(it)
                    }
                }
            }.milliseconds
            Timber.tag(className).v("Execution of $className took $executionDuration")
            result
        } catch (e: Exception) {
            Timber.tag(className).e(e, "Exception occurred while executing $className with parameters $params")
            Result.failure(e)
        }
    }

    protected abstract suspend fun execute(params: P): R

    public companion object {
        private const val TAG = "UseCase"
    }

}

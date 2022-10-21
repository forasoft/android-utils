package com.forasoft.androidutils.clean.usecase.parameterless

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class UseCaseTest {

    private val dispatcher = StandardTestDispatcher()

    @Test
    fun invoke_whenOperationSucceeds_returnsResultSuccess() = runTest(dispatcher) {
        val useCase = GetOneUseCase(dispatcher)

        val result = useCase()

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(1)
    }

    @Test
    fun invoke_whenOperationFails_returnsResultFailure() = runTest(dispatcher) {
        val useCase = GetIOExceptionUseCase(dispatcher)

        val result = useCase()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IOException::class.java)
    }

    private class GetOneUseCase(dispatcher: CoroutineDispatcher) : UseCase<Int>(dispatcher) {
        override suspend fun execute(params: Unit): Int = 1
    }

    private class GetIOExceptionUseCase(dispatcher: CoroutineDispatcher) : UseCase<Int>(dispatcher) {
        override suspend fun execute(params: Unit): Int = throw IOException()
    }

}

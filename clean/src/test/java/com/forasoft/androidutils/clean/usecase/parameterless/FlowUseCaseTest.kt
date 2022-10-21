package com.forasoft.androidutils.clean.usecase.parameterless

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class FlowUseCaseTest {

    @Test
    fun invoke_whenOperationSucceeds_returnsResultSuccess() = runTest {
        val useCase = GetOneUseCase(UnconfinedTestDispatcher())

        val result = useCase().first()

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(1)
    }

    @Test
    fun invoke_whenOperationFails_returnsResultFailure() = runTest {
        val useCase = GetIOExceptionUseCase(UnconfinedTestDispatcher())

        val result = useCase().first()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IOException::class.java)
    }

    private class GetOneUseCase(dispatcher: CoroutineDispatcher) : FlowUseCase<Int>(dispatcher) {
        override fun execute(params: Unit): Flow<Result<Int>> {
            return flow { emit(1) }.map { Result.success(it) }
        }
    }

    private class GetIOExceptionUseCase(dispatcher: CoroutineDispatcher) :
        FlowUseCase<Int>(dispatcher) {
        override fun execute(params: Unit): Flow<Result<Int>> {
            return flow<Int> { throw IOException() }.map { Result.success(it) }
        }
    }

}

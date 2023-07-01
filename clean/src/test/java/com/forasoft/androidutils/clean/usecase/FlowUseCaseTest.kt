package com.forasoft.androidutils.clean.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FlowUseCaseTest {

    private lateinit var divideInt: DivideIntUseCase

    @Before
    fun before() {
        divideInt = DivideIntUseCase(UnconfinedTestDispatcher())
    }

    @Test
    fun invoke_whenOperationSucceeds_returnsResultSuccess() = runTest {
        val dividend = 4
        val divisor = 2
        val params = DivideIntUseCase.Params(dividend, divisor)

        val result = divideInt(params).first()

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(dividend / divisor)
    }

    @Test
    fun invoke_whenOperationFails_returnsResultFailure() = runTest {
        val params = DivideIntUseCase.Params(4, 0)

        val result = divideInt(params).first()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(ArithmeticException::class.java)
    }

    private class DivideIntUseCase(
        dispatcher: CoroutineDispatcher
    ) : FlowUseCase<DivideIntUseCase.Params, Int>(dispatcher) {

        override fun execute(params: Params): Flow<Int> {
            val (dividend, divisor) = params
            return flow {
                emit(dividend / divisor)
            }
        }

        data class Params(val dividend: Int, val divisor: Int)

    }

}

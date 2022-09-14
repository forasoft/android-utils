package com.forasoft.androidutils.clean.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UseCaseTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var divideInt: DivideIntUseCase

    @Before
    fun before() {
        divideInt = DivideIntUseCase(dispatcher)
    }

    @Test
    fun invoke_whenOperationSucceeds_returnsResultSuccess() = runTest(dispatcher) {
        val dividend = 4
        val divisor = 2
        val params = DivideIntUseCase.Params(dividend, divisor)

        val result = divideInt(params)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(dividend / divisor)
    }

    @Test
    fun invoke_whenOperationFails_returnsResultFailure() = runTest(dispatcher) {
        val params = DivideIntUseCase.Params(4, 0)

        val result = divideInt(params)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(ArithmeticException::class.java)
    }

    private class DivideIntUseCase(
        dispatcher: CoroutineDispatcher,
    ) : UseCase<DivideIntUseCase.Params, Int>(dispatcher) {

        override suspend fun execute(params: Params): Int {
            val (dividend, divisor) = params
            return dividend / divisor
        }

        data class Params(val dividend: Int, val divisor: Int)
    }

}

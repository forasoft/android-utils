package com.forasoft.androidutils.clean.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SimpleUseCaseTest {

    private lateinit var divideInt: DivideIntUseCase

    @Before
    fun before() {
        divideInt = DivideIntUseCase()
    }

    @Test
    fun invoke_whenOperationSucceeds_returnsResult() {
        val dividend = 4
        val divisor = 2
        val params = DivideIntUseCase.Params(dividend, divisor)

        val result = divideInt(params)

        assertThat(result).isEqualTo(dividend / divisor)
    }

    @Test(expected = ArithmeticException::class)
    fun invoke_whenOperationFails_throwsException() {
        val params = DivideIntUseCase.Params(4, 0)
        divideInt(params)
    }

    private class DivideIntUseCase : SimpleUseCase<DivideIntUseCase.Params, Int> {

        override fun invoke(params: Params): Int {
            val (dividend, divisor) = params
            return dividend / divisor
        }

        data class Params(val dividend: Int, val divisor: Int)
    }

}

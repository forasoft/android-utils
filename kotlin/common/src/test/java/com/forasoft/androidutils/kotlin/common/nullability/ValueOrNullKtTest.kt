package com.forasoft.androidutils.kotlin.common.nullability

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test

class ValueOrNullKtTest {

    @Test
    fun valueOrNull_withNonThrowingOperation_returnsValue() {
        val returnValue = true
        val operation = { returnValue }

        val result = valueOrNull<Boolean, Exception> { operation() }

        assertThat(result).isEqualTo(returnValue)
    }

    @Test
    fun valueOrNull_withExpectedThrowable_returnsNull() {
        @Suppress("UNREACHABLE_CODE")
        val operation = {
            error(Unit)
            true
        }

        val result = valueOrNull<Boolean, IllegalStateException> { operation() }

        assertThat(result).isEqualTo(null)
    }

    @Test
    fun valueOrNull_withExpectedThrowableSubclass_returnsNull() {
        @Suppress("UNREACHABLE_CODE")
        val operation = {
            error(Unit)
            true
        }

        val result = valueOrNull<Boolean, Exception> { operation() }

        assertThat(result).isEqualTo(null)
    }

    @Test
    fun valueOrNull_withUnexpectedThrowable_rethrows() {
        @Suppress("UNREACHABLE_CODE")
        val operation = {
            error(Unit)
            true
        }

        assertThrows(IllegalStateException::class.java) {
            valueOrNull<Boolean, IllegalArgumentException> { operation() }
        }
    }
}

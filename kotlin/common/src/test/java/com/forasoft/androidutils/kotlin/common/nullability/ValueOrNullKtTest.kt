package com.forasoft.androidutils.kotlin.common.nullability

import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test

class ValueOrNullKtTest {

    @Test
    fun valueOrNull_withNonThrowingOperation_returnsValue() {
        val returnValue = true
        val operation = { returnValue }

        val result = valueOrNull<Exception, Boolean> { operation() }

        Truth.assertThat(result).isEqualTo(returnValue)
    }

    @Test
    fun valueOrNull_withExpectedThrowable_returnsNull() {
        @Suppress("UNREACHABLE_CODE")
        val operation = {
            error(Unit)
            true
        }

        val result = valueOrNull<IllegalStateException, Boolean> { operation() }

        Truth.assertThat(result).isEqualTo(null)
    }

    @Test
    fun valueOrNull_withExpectedThrowableSubclass_returnsNull() {
        @Suppress("UNREACHABLE_CODE")
        val operation = {
            error(Unit)
            true
        }

        val result = valueOrNull<Exception, Boolean> { operation() }

        Truth.assertThat(result).isEqualTo(null)
    }

    @Test
    fun valueOrNull_withUnexpectedThrowable_rethrows() {
        @Suppress("UNREACHABLE_CODE")
        val operation = {
            error(Unit)
            true
        }

        assertThrows(IllegalStateException::class.java) {
            valueOrNull<IllegalArgumentException, Boolean> { operation() }
        }
    }
}

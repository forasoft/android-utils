package com.forasoft.androidutils.kotlin.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ByteArrayKtTest {

    @Test
    fun toHexString_returnsCorrectString() {
        val byteArray = "Sample byte array".toByteArray()
        val expected = byteArray.joinToString("") { "%02x".format(it) }

        val actual = byteArray.toHexString()

        assertThat(actual).isEqualTo(expected)
    }

}

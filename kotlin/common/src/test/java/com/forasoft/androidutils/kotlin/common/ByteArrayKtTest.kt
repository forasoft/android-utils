package com.forasoft.androidutils.kotlin.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ByteArrayKtTest {

    @Test
    fun toHexString_whenByteArrayIsNotEmpty_returnsCorrectString() {
        val byteArray = byteArrayOf(0, 1, 10, 100, 1000.toByte())

        val actual = byteArray.toHexString()
        println(actual)

        assertThat(actual).isEqualTo("00 01 0A 64 E8")
    }

    @Test
    fun toHexString_whenByteArrayIsEmpty_returnsEmptyString() {
        val byteArray = byteArrayOf()

        val hexString = byteArray.toHexString()

        assertThat(hexString).isEmpty()
    }

}

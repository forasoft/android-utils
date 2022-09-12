package com.forasoft.androidutils.kotlin.common

import com.google.common.truth.Truth
import org.junit.Test

class EnumKtTest {

    @Test
    fun enumValueOfOrNull_whenEntryFound_returnsEnumEntry() {
        val expected = TestEnum.ENTRY
        val entry = enumValueOfOrNull<TestEnum>(expected.name)

        Truth.assertThat(entry).isEqualTo(expected)
    }

    @Test
    fun enumValueOfOrNull_whenEntryNotFound_returnsNull() {
        val entry = enumValueOfOrNull<TestEnum>("")

        Truth.assertThat(entry).isEqualTo(null)
    }

}

private enum class TestEnum { ENTRY }

package com.forasoft.androidutils.kotlin.common.size

/**
 * Basic units of information
 *
 * @property bitCount size in bits
 * @property abbreviation standard abbreviation
 */
enum class SizeUnit(val bitCount: Int, val abbreviation: String) {
    BIT(bitCount = 1, abbreviation = "bit"),
    BYTE(bitCount = 8, abbreviation = "B")
}

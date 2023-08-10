package com.forasoft.androidutils.kotlin.common.size

/**
 * Basic units of information
 *
 * @property bitCount size in bits
 * @property abbreviation standard abbreviation
 */
public enum class SizeUnit(public val bitCount: Int, public val abbreviation: String) {
    BIT(bitCount = 1, abbreviation = "bit"),
    BYTE(bitCount = 8, abbreviation = "B"),
}

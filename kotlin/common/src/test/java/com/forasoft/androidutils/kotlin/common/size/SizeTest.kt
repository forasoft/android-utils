package com.forasoft.androidutils.kotlin.common.size

import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test
import java.math.BigInteger

class SizeTest {

    @Test
    fun getBitCount_ofZero_returnsCorrectly() {
        val amount = 0
        val size = amount.gibibytes

        val bitCount = size.bitCount

        val expectedBitCount = 0.toBigInteger()
        assertThat(bitCount).isEqualTo(expectedBitCount)
    }

    @Test
    fun getBitCount_ofIntegerAmount_returnsCorrectly() {
        val amount = 12
        val size = amount.gibibytes

        val bitCount = size.bitCount

        val expectedBitCount = amount.toBigInteger()
            .times(BITS_IN_BYTE.toBigInteger())
            .times(BINARY_MULTIPLIER.toBigInteger())
            .times(BINARY_MULTIPLIER.toBigInteger())
            .times(BINARY_MULTIPLIER.toBigInteger())
        assertThat(bitCount).isEqualTo(expectedBitCount)
    }

    @Test
    fun getBitCount_ofDoubleAmount_returnsCorrectly() {
        val amount = 1.5
        val size = amount.kilobytes

        val bitCount = size.bitCount

        val expectedBitCount = amount * BITS_IN_BYTE * SI_MULTIPLIER
        assertThat(bitCount.toBigDecimal()).isEqualToIgnoringScale(expectedBitCount.toBigDecimal())
    }

    @Test
    fun getBitCount_ofNegativeAmount_returnsCorrectly() {
        val amount = -200
        val size = amount.kilobytes

        val bitCount = size.bitCount

        val expectedBitCount = amount * BITS_IN_BYTE * SI_MULTIPLIER
        assertThat(bitCount.toBigDecimal()).isEqualToIgnoringScale(expectedBitCount.toBigDecimal())
    }

    @Test
    fun getBitCount_ofBigDecimalAmount_returnsCorrectly() {
        val amount = 10.toBigInteger().pow(100)
        val size = amount.kibibits

        val bitCount = size.bitCount

        val expectedBitCount = amount * BINARY_MULTIPLIER.toBigInteger()
        assertThat(bitCount).isEqualTo(expectedBitCount)
    }

    @Test
    fun minus_withNegativeSubtrahend_returnsCorrectly() {
        val amount = 10
        val subtrahendAmount = -223
        val size = amount.kibibits
        val subtrahendSize = subtrahendAmount.bits

        val difference = size - subtrahendSize

        val expectedDifference =
            Size((amount * BINARY_MULTIPLIER - subtrahendAmount).toBigInteger())
        assertThat(difference).isEqualTo(expectedDifference)
    }

    @Test
    fun minus_withBiggerSubtrahend_returnsCorrectly() {
        val amount = 10
        val subtrahendAmount = 223
        val size = amount.bits
        val subtrahendSize = subtrahendAmount.bits

        val difference = size - subtrahendSize

        val expectedDifference = Size((amount - subtrahendAmount).toBigInteger())
        assertThat(difference).isEqualTo(expectedDifference)
    }

    @Test
    fun plus_returnsCorrectly() {
        val amount = 10
        val summandAmount = 1.2
        val size = amount.bits
        val subtrahendSize = summandAmount.kibibits

        val sum = size + subtrahendSize

        val expectedSum =
            Size((amount + summandAmount * BINARY_MULTIPLIER).toBigDecimal().toBigInteger())
        assertThat(sum).isEqualTo(expectedSum)
    }

    @Test
    fun times_returnsCorrectly() {
        val amount = 10
        val multiplier = 1.2
        val size = amount.bits

        val product = size * multiplier

        val expectedProduct = Size((amount * multiplier).toBigDecimal().toBigInteger())
        assertThat(product).isEqualTo(expectedProduct)
    }

    @Test
    fun times_withZeroMultiplier_returnsZero() {
        val amount = 10
        val multiplier = 0
        val size = amount.bits

        val product = size * multiplier

        val expectedProduct = Size(BigInteger.ZERO)
        assertThat(product).isEqualTo(expectedProduct)
    }

    @Test
    fun div_returnsCorrectly() {
        val amount = 1
        val divisor = 2
        val size = amount.kibibits

        val result = size / divisor

        val expectedResult =
            Size((amount.toDouble() * BINARY_MULTIPLIER / 2).toBigDecimal().toBigInteger())
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun div_roundsUp() {
        val amount = 1
        val divisor = 3
        val size = amount.bits

        val result = size / divisor

        val expectedResult = Size(1.toBigInteger())
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun compare_returnsCorrectly() {
        val size = 12.gigabytes
        val biggerSize = 12.gibibytes

        val result = size.compareTo(biggerSize)

        assertThat(result).isEqualTo(-1)
    }

    @Test
    fun coerceAtLeast_withBiggerValue_returnsOriginal() {
        val size = 12.gigabytes
        val minSize = 1.bits

        val result = size.coerceAtLeast(minSize)

        assertThat(result).isEqualTo(size)
    }

    @Test
    fun coerceAtLeast_withLesserValue_returnsMinimum() {
        val size = (-12).gigabytes
        val minSize = 1.bits

        val result = size.coerceAtLeast(minSize)

        assertThat(result).isEqualTo(minSize)
    }

    @Test
    fun coerceAtMost_withBiggerValue_returnsMaximum() {
        val size = 12.gigabytes
        val maxSize = 1.bits

        val result = size.coerceAtMost(maxSize)

        assertThat(result).isEqualTo(maxSize)
    }

    @Test
    fun coerceAtMost_withLesserValue_returnsOriginal() {
        val size = (-12).gigabytes
        val maxSize = 0.bits

        val result = size.coerceAtMost(maxSize)

        assertThat(result).isEqualTo(size)
    }

    @Test
    fun coerceIn_withUpperBoundLessThanLowerBound_throws() {
        val size = (-12).gigabytes
        val minSize = 10.bits
        val maxSize = 0.bits

        assertThrows(IllegalArgumentException::class.java) { size.coerceIn(minSize, maxSize) }
    }

    @Test
    fun coerceIn_withLesserValue_returnsLowerBound() {
        val size = (-12).gigabytes
        val minSize = 0.bits
        val maxSize = 10.bits

        val result = size.coerceIn(minSize, maxSize)

        assertThat(result).isEqualTo(minSize)
    }

    @Test
    fun coerceIn_withBiggerValue_returnsUpperBound() {
        val size = (12).gigabytes
        val minSize = 0.bits
        val maxSize = 10.bits

        val result = size.coerceIn(minSize, maxSize)

        assertThat(result).isEqualTo(maxSize)
    }

    @Test
    fun coerceIn_withValueBetween_returnOriginal() {
        val size = (12).gigabytes
        val minSize = 0.bits
        val maxSize = 20.gibibytes

        val result = size.coerceIn(minSize, maxSize)

        assertThat(result).isEqualTo(size)
    }

    @Test
    fun getValue_withoutPrefix_returnsCorrectly() {
        val amount = 12
        val size = amount.mebibytes

        val value = size.getValue(SizeUnit.BYTE)

        val expectedValue = (amount * BINARY_MULTIPLIER * BINARY_MULTIPLIER).toBigDecimal()
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getValue_ofBitsWithoutPrefix_returnsCorrectly() {
        val amount = 12
        val size = amount.mebibytes

        val value = size.getValue(SizeUnit.BIT)

        val expectedValue =
            (amount * BINARY_MULTIPLIER * BINARY_MULTIPLIER * BITS_IN_BYTE).toBigDecimal()
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getValue_ofBiggerUnit_returnsCorrectly() {
        val amount = 12
        val size = amount.bits

        val value = size.getValue(SizePrefix.Kibi, SizeUnit.BIT)

        val expectedValue =
            (amount.toBigDecimal().divide(BINARY_MULTIPLIER.toBigDecimal()))
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getValue_ofSmallerUnit_returnsCorrectly() {
        val amount = 12
        val size = amount.tebibits

        val value = size.getValue(SizePrefix.Gibi, SizeUnit.BIT)

        val expectedValue =
            (amount.toBigDecimal().times(BINARY_MULTIPLIER.toBigDecimal()))
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedSiValue_withValueOfThreshold_returnsCorrectly() {
        val amount = 100_000
        val size = amount.bytes
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedSiValue(SizeUnit.BYTE, threshold)

        val expectedPrefix = SizePrefix.Kilo
        val expectedValue = amount.toBigDecimal().divide(SI_MULTIPLIER.toBigDecimal())
        assertThat(prefix).isEqualTo(expectedPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedSiValue_withValueJustOverThreshold_returnsCorrectly() {
        val amount = 100_001
        val size = amount.bytes
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedSiValue(SizeUnit.BYTE, threshold)

        val expectedPrefix = SizePrefix.Mega
        val expectedValue =
            amount.toBigDecimal().divide((SI_MULTIPLIER * SI_MULTIPLIER).toBigDecimal())
        assertThat(prefix).isEqualTo(expectedPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedSiValue_withSmallBareUnit_returnsCorrectly() {
        val amount = 2
        val size = amount.bytes
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedSiValue(SizeUnit.BYTE, threshold)

        val expectedPrefix = null
        val expectedValue = amount.toBigDecimal()
        assertThat(prefix).isEqualTo(expectedPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedSiValue_biggerThanBiggestPrefix_returnsCorrectly() {
        val biggestPrefix = SizePrefix.siPrefixes.last()
        val amount = 2_000
        val size = Size.of(amount, biggestPrefix, SizeUnit.BYTE)
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedSiValue(SizeUnit.BYTE, threshold)

        val expectedValue = amount.toBigDecimal()
        assertThat(prefix).isEqualTo(biggestPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedBinaryValue_withValueOfThreshold_returnsCorrectly() {
        val amount = 100_000
        val size = amount.bytes
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedBinaryValue(SizeUnit.BYTE, threshold)

        val expectedPrefix = SizePrefix.Kibi
        val expectedValue = amount.toBigDecimal().divide(BINARY_MULTIPLIER.toBigDecimal())
        assertThat(prefix).isEqualTo(expectedPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedBinaryValue_withValueJustOverThreshold_returnsCorrectly() {
        val amount = (100 * BINARY_MULTIPLIER + 1).toBigInteger()
        val size = amount.bytes
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedBinaryValue(SizeUnit.BYTE, threshold)

        val expectedPrefix = SizePrefix.Mebi
        val expectedValue =
            amount.toBigDecimal().divide((BINARY_MULTIPLIER * BINARY_MULTIPLIER).toBigDecimal())
        assertThat(prefix).isEqualTo(expectedPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedBinaryValue_withSmallBareUnit_returnsCorrectly() {
        val amount = 2
        val size = amount.bytes
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedBinaryValue(SizeUnit.BYTE, threshold)

        val expectedPrefix = null
        val expectedValue = amount.toBigDecimal()
        assertThat(prefix).isEqualTo(expectedPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getPrefixedBinaryValue_biggerThanBiggestPrefix_returnsCorrectly() {
        val biggestPrefix = SizePrefix.binaryPrefixes.last()
        val amount = 2_000
        val size = Size.of(amount, biggestPrefix, SizeUnit.BYTE)
        val threshold = 100.toBigDecimal()

        val (prefix, value) = size.getPrefixedBinaryValue(SizeUnit.BYTE, threshold)

        val expectedValue = amount.toBigDecimal()
        assertThat(prefix).isEqualTo(biggestPrefix)
        assertThat(value).isEqualToIgnoringScale(expectedValue)
    }

    @Test
    fun getString_returnsCorrectly() {
        val amount = 123
        val size = amount.gigabytes

        val string = size.toString()

        val expectedAmountString = amount.toBigDecimal()
            .times(SI_MULTIPLIER.toBigDecimal())
            .times(SI_MULTIPLIER.toBigDecimal())
            .times(SI_MULTIPLIER.toBigDecimal())
            .divide(BINARY_MULTIPLIER.toBigDecimal())
            .divide(BINARY_MULTIPLIER.toBigDecimal())
            .divide(BINARY_MULTIPLIER.toBigDecimal()).run {
                "%.2f".format(this.toDouble())
            }
        val expectedValue =
            "${expectedAmountString}${SizePrefix.Gibi.abbreviation}${SizeUnit.BYTE.abbreviation}"
        assertThat(string).isEqualTo(expectedValue)
    }

    @Test
    fun sumOf_returnsCorrectly() {
        val amounts = listOf(12, 23, 34, 45, 6, 7)
        val sizes = amounts.map { it.kibibits }

        val sum = sizes.sumOf()

        val expectedSum =
            amounts.sumOf { it }.times(BINARY_MULTIPLIER).run { Size(this.toBigInteger()) }
        assertThat(sum).isEqualTo(expectedSum)
    }

    companion object {
        private const val BITS_IN_BYTE = 8

        private const val SI_MULTIPLIER = 1000
        private const val BINARY_MULTIPLIER = 1024
    }

}

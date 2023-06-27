package com.forasoft.androidutils.kotlin.common.size

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * Represents a size of the information
 *
 * @property bitCount size in bits
 */
@JvmInline
value class Size internal constructor(val bitCount: BigInteger) {

    operator fun minus(other: Size) = Size(this.bitCount - other.bitCount)

    operator fun plus(other: Size) = Size(this.bitCount + other.bitCount)

    operator fun times(other: Number): Size {
        val otherBigDecimal = when (other) {
            is Double, is Float -> other.toDouble().toBigDecimal()
            is BigDecimal -> other
            is BigInteger -> other.toBigDecimal()
            else -> other.toLong().toBigDecimal()
        }

        val bitCount = this.bitCount
            .toBigDecimal()
            .times(otherBigDecimal)
            .toBigInteger()
        return Size(bitCount)
    }

    operator fun div(other: Number): Size {
        val otherBigDecimal = when (other) {
            is Double, is Float -> other.toDouble().toBigDecimal()
            is BigDecimal -> other
            is BigInteger -> other.toBigDecimal()
            else -> other.toLong().toBigDecimal()
        }

        val bitCount = this.bitCount
            .toBigDecimal()
            .divide(otherBigDecimal, RoundingMode.CEILING)
            .toBigInteger()

        return Size(bitCount)
    }

    operator fun compareTo(another: Size): Int {
        return this.bitCount.compareTo(another.bitCount)
    }

    /**
     * Returns this size in passed units ([prefix] and [unit])
     *
     * Example:
     * ```
     * val size = 8.kilobytes
     * size.getValue(SizePrefix.Mega, SizeUnit.bits) # 0.001
     * ```
     *
     * @param prefix prefix to be used in size calculation
     * @param unit output units
     */
    fun getValue(prefix: SizePrefix? = null, unit: SizeUnit): BigDecimal {
        var unitCount = this.bitCount.toBigDecimal().divide(unit.bitCount.toBigDecimal())
        if (prefix != null) unitCount = unitCount.divide(prefix.multiplier.toBigDecimal())
        return unitCount
    }

    /**
     * Returns this size in passed units
     *
     * Example:
     * ```
     * val size = 8.kilobytes
     * size.getValue(SizeUnit.bits) # 8000
     * ```
     *
     * @param unit output units
     */
    fun getValue(unit: SizeUnit) = getValue(null, unit)

    /**
     * Returns a [SizePrefix] and amount of [unit] that corresponds to this [Size].
     *
     * The returned prefix is the smallest [SizePrefix.SiPrefix] with which the amount of units
     * doesn't exceed passed [threshold]
     *
     * @see getPrefixedBinaryValue
     *
     * @param unit output units
     * @param threshold largest unit amount allowed without moving to the next (bigger) prefix
     */
    fun getPrefixedSiValue(
        unit: SizeUnit,
        threshold: BigDecimal,
    ): Pair<SizePrefix.SiPrefix?, BigDecimal> =
        getPrefixedValue(unit, threshold, SizePrefix.siPrefixes)

    /**
     * Returns a [SizePrefix] and amount of [unit] that corresponds to this [Size].
     *
     * The returned prefix is the smallest [SizePrefix.BinaryPrefix] with which the amount of units
     * doesn't exceed passed [threshold]
     *
     * @see getPrefixedSiValue
     *
     * @param unit output units
     * @param threshold largest unit amount allowed without moving to the next (bigger) prefix
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getPrefixedBinaryValue(
        unit: SizeUnit,
        threshold: BigDecimal,
    ): Pair<SizePrefix.BinaryPrefix?, BigDecimal> =
        getPrefixedValue(unit, threshold, SizePrefix.binaryPrefixes)

    private fun <T : SizePrefix> getPrefixedValue(
        unit: SizeUnit,
        threshold: BigDecimal,
        prefixes: List<T>,
    ): Pair<T?, BigDecimal> {
        val unitCount = (this / unit.bitCount).bitCount.toBigDecimal()
        val prefixIterator = prefixes.iterator()

        var prefix: T? = null
        var value = unitCount
        while (value > threshold && prefixIterator.hasNext()) {
            prefix = prefixIterator.next()
            value = unitCount.divide(prefix.multiplier.toBigDecimal())
        }
        return prefix to value
    }

    override fun toString(): String {
        val (prefix, value) = getPrefixedBinaryValue(SizeUnit.BYTE, 1024.toBigDecimal())
        return "${"%.2f".format(value.toDouble())}${prefix?.abbreviation ?: ""}${SizeUnit.BYTE.abbreviation}"
    }

    companion object {
        internal fun of(
            amount: Number = 1,
            prefix: SizePrefix? = null,
            unit: SizeUnit = SizeUnit.BYTE,
        ): Size {

            val amountBigDecimal = when (amount) {
                is Double, is Float -> amount.toDouble().toBigDecimal()
                is BigInteger -> amount.toBigDecimal()
                is BigDecimal -> amount
                else -> amount.toLong().toBigDecimal()
            }

            val bitCount = amountBigDecimal
                .times(unit.bitCount.toBigDecimal())
                .times(prefix?.multiplier?.toBigDecimal() ?: BigDecimal.ONE)
                .round(MathContext.UNLIMITED)
                .toBigInteger()

            return Size(bitCount)
        }
    }
}

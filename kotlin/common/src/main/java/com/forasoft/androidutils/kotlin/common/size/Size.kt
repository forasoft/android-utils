@file:Suppress("unused")

package com.forasoft.androidutils.kotlin.common.size

import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

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
            .div(otherBigDecimal)
            .toBigInteger()

        return Size(bitCount)
    }

    operator fun compareTo(another: Size): Int {
        return this.bitCount.compareTo(another.bitCount)
    }

    fun coerceAtLeast(minimumValue: Size): Size {
        return if (this < minimumValue) minimumValue else this
    }

    fun getValue(prefix: SizePrefix? = null, unit: SizeUnit): BigDecimal {
        var unitCount = this.bitCount.toBigDecimal() / unit.bitCount.toBigDecimal()
        if (prefix != null) unitCount /= prefix.multiplier.toBigDecimal()
        return unitCount
    }

    fun getValue(unit: SizeUnit) = getValue(null, unit)

    fun getPrefixedSiValue(
        unit: SizeUnit,
        threshold: BigDecimal,
    ): Pair<SizePrefix.SiPrefix?, BigDecimal> =
        getPrefixedValue(unit, threshold, SizePrefix.siPrefixes)

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
            value = prefix.multiplier.let { unitCount / it.toBigDecimal() }
        }
        return prefix to value
    }

    override fun toString(): String {
        val (prefix, value) = getPrefixedBinaryValue(SizeUnit.BYTE, 1024.toBigDecimal())
        return "${"%.2f".format(value.toFloat())}${prefix?.abbreviation ?: ""}${SizeUnit.BYTE.abbreviation}\""
    }

    companion object {
        internal fun of(
            amount: Number = 1,
            prefix: SizePrefix? = null,
            unit: SizeUnit = SizeUnit.BYTE,
        ): Size {

            val applyMultipliers: BigDecimal.() -> BigInteger = {
                this
                    .times(unit.bitCount.toBigDecimal())
                    .times(prefix?.multiplier?.toBigDecimal() ?: BigDecimal.ONE)
                    .round(MathContext.UNLIMITED)
                    .toBigInteger()
            }

            val bitCount = when (amount) {
                is Double, is Float -> amount
                    .toDouble()
                    .toBigDecimal()
                    .applyMultipliers()

                is BigInteger -> amount
                    .toBigDecimal()
                    .applyMultipliers()

                is BigDecimal -> amount
                    .applyMultipliers()

                else -> amount
                    .toLong()
                    .toBigDecimal()
                    .applyMultipliers()
            }

            return Size(bitCount)
        }
    }
}

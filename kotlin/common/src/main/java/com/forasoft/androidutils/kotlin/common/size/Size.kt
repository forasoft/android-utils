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
        val bitCount = when (other) {
            is Double, is Float -> this.bitCount
                .toBigDecimal()
                .times(other.toDouble().toBigDecimal())
                .toBigInteger()

            else -> this.bitCount
                .times(other.toLong().toBigInteger())
        }
        return Size(bitCount)
    }

    operator fun div(other: Number): Size {
        val bitCount = when (other) {
            is Double, is Float -> this.bitCount
                .toBigDecimal()
                .div(other.toDouble().toBigDecimal())
                .toBigInteger()

            else -> this.bitCount.toBigDecimal()
                .div(other.toLong().toBigDecimal())
                .toBigInteger()
        }
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
        fun of(
            amount: Number = 1,
            prefix: SizePrefix? = null,
            unit: SizeUnit = SizeUnit.BYTE,
        ): Size {
            val bitCount = when (amount) {
                is Double, is Float -> amount
                    .toDouble()
                    .toBigDecimal()
                    .times(unit.bitCount.toBigDecimal())
                    .times(prefix?.multiplier?.toBigDecimal() ?: BigDecimal.ONE)
                    .round(MathContext.UNLIMITED)
                    .toBigInteger()

                else -> amount
                    .toLong()
                    .toBigInteger()
                    .times(unit.bitCount.toBigInteger())
                    .times(prefix?.multiplier ?: BigInteger.ONE)
            }

            return Size(bitCount)
        }
    }
}

enum class SizeUnit(val bitCount: Int, val abbreviation: String) {
    BIT(bitCount = 1, abbreviation = "bit"),
    BYTE(bitCount = 8, abbreviation = "B")
}

sealed interface SizePrefix {

    val abbreviation: String
    val multiplier: BigInteger

    interface SiPrefix : SizePrefix
    interface BinaryPrefix : SizePrefix

    object Kilo : SiPrefix {
        override val multiplier = siMultiplier
        override val abbreviation = "k"
    }

    object Mega : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(2)
        override val abbreviation = "M"
    }

    object Giga : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(3)
        override val abbreviation = "G"
    }

    object Tera : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(4)
        override val abbreviation = "T"
    }

    object Peta : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(5)
        override val abbreviation = "P"
    }

    object Exa : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(6)
        override val abbreviation = "E"
    }

    object Zetta : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(7)
        override val abbreviation = "Z"
    }

    object Yotta : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(8)
        override val abbreviation = "Y"
    }

    object Kibi : BinaryPrefix {
        override val multiplier = binaryMultiplier
        override val abbreviation = "Ki"
    }

    object Mebi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(2)
        override val abbreviation = "Mi"
    }

    object Gibi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(3)
        override val abbreviation = "Gi"
    }

    object Tebi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(4)
        override val abbreviation = "Ti"
    }

    object Pebi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(5)
        override val abbreviation = "Pi"
    }

    object Exbi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(6)
        override val abbreviation = "Ei"
    }

    companion object {
        private val binaryMultiplier = 1024.toBigInteger()
        private val siMultiplier = 1000.toBigInteger()

        val siPrefixes
            get() = listOf(Kilo, Mega, Giga, Tera, Peta, Exa, Zetta, Yotta)

        val binaryPrefixes
            get() = listOf(Kibi, Mebi, Gibi, Tebi, Pebi, Exbi)
    }

}

fun Iterable<Size>.sumOf() = Size(this.sumOf { it.bitCount })

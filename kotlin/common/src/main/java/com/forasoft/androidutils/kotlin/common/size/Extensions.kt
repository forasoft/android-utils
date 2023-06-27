package com.forasoft.androidutils.kotlin.common.size


/**
 * Returns the [Size] equal to the sum of the sizes in the iterable
 */
fun Iterable<Size>.sumOf() = Size(this.sumOf { it.bitCount })

/**
 * Ensures that the size is not less than the passed [minimumValue]
 */
fun Size.coerceAtLeast(minimumValue: Size): Size {
    return if (this < minimumValue) minimumValue else this
}

/**
 * Ensures that the size is not greater than the passed [maximumValue]
 */
fun Size.coerceAtMost(maximumValue: Size): Size {
    return if (this > maximumValue) maximumValue else this
}

/**
 * Ensures that this value lies in the specified range [minimumValue]..[maximumValue]
 */
fun Size.coerceIn(minimumValue: Size, maximumValue: Size): Size {
    require(minimumValue <= maximumValue) { "Lower bound can't be bigger than upper bound" }
    return when {
        this > maximumValue -> maximumValue
        this < minimumValue -> minimumValue
        else -> this
    }
}

val Number.bytes: Size
    get() = Size.of(this, null, SizeUnit.BYTE)

val Number.kilobytes: Size
    get() = Size.of(this, SizePrefix.Kilo, SizeUnit.BYTE)

val Number.megabytes: Size
    get() = Size.of(this, SizePrefix.Mega, SizeUnit.BYTE)

val Number.gigabytes: Size
    get() = Size.of(this, SizePrefix.Giga, SizeUnit.BYTE)

val Number.terabytes: Size
    get() = Size.of(this, SizePrefix.Tera, SizeUnit.BYTE)

val Number.petabytes: Size
    get() = Size.of(this, SizePrefix.Peta, SizeUnit.BYTE)

val Number.exabytes: Size
    get() = Size.of(this, SizePrefix.Exa, SizeUnit.BYTE)

val Number.zettabytes: Size
    get() = Size.of(this, SizePrefix.Zetta, SizeUnit.BYTE)

val Number.yottabytes: Size
    get() = Size.of(this, SizePrefix.Yotta, SizeUnit.BYTE)

val Number.kibibytes: Size
    get() = Size.of(this, SizePrefix.Kibi, SizeUnit.BYTE)

val Number.mebibytes: Size
    get() = Size.of(this, SizePrefix.Mebi, SizeUnit.BYTE)

val Number.gibibytes: Size
    get() = Size.of(this, SizePrefix.Gibi, SizeUnit.BYTE)

val Number.tebibytes: Size
    get() = Size.of(this, SizePrefix.Tebi, SizeUnit.BYTE)

val Number.pebibytes: Size
    get() = Size.of(this, SizePrefix.Pebi, SizeUnit.BYTE)

val Number.exbibytes: Size
    get() = Size.of(this, SizePrefix.Exbi, SizeUnit.BYTE)

val Number.bits: Size
    get() = Size.of(this, null, SizeUnit.BIT)

val Number.kilobits: Size
    get() = Size.of(this, SizePrefix.Kilo, SizeUnit.BIT)

val Number.megabits: Size
    get() = Size.of(this, SizePrefix.Mega, SizeUnit.BIT)

val Number.gigabits: Size
    get() = Size.of(this, SizePrefix.Giga, SizeUnit.BIT)

val Number.terabits: Size
    get() = Size.of(this, SizePrefix.Tera, SizeUnit.BIT)

val Number.petabits: Size
    get() = Size.of(this, SizePrefix.Peta, SizeUnit.BIT)

val Number.exabits: Size
    get() = Size.of(this, SizePrefix.Exa, SizeUnit.BIT)

val Number.zettabits: Size
    get() = Size.of(this, SizePrefix.Zetta, SizeUnit.BIT)

val Number.yottabits: Size
    get() = Size.of(this, SizePrefix.Yotta, SizeUnit.BIT)

val Number.kibibits: Size
    get() = Size.of(this, SizePrefix.Kibi, SizeUnit.BIT)

val Number.mebibits: Size
    get() = Size.of(this, SizePrefix.Mebi, SizeUnit.BIT)

val Number.gibibits: Size
    get() = Size.of(this, SizePrefix.Gibi, SizeUnit.BIT)

val Number.tebibits: Size
    get() = Size.of(this, SizePrefix.Tebi, SizeUnit.BIT)

val Number.pebibits: Size
    get() = Size.of(this, SizePrefix.Pebi, SizeUnit.BIT)

val Number.exbibits: Size
    get() = Size.of(this, SizePrefix.Exbi, SizeUnit.BIT)

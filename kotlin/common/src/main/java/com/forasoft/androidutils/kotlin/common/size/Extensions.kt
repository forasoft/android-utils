package com.forasoft.androidutils.kotlin.common.size


/**
 * Returns the [Size] equal to the sum of the sizes in the iterable
 */
@Suppress("unused")
fun Iterable<Size>.sumOf() = Size(this.sumOf { it.bitCount })

/**
 * Ensures that the size is not less than the passed [minimumValue]
 */
@Suppress("unused")
fun Size.coerceAtLeast(minimumValue: Size): Size {
    return if (this < minimumValue) minimumValue else this
}

/**
 * Ensures that the size is not greater than the passed [maximumValue]
 */
@Suppress("unused")
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

@Suppress("unused")
val Number.bytes: Size
    get() = Size.of(this, null, SizeUnit.BYTE)

@Suppress("unused")
val Number.kilobytes: Size
    get() = Size.of(this, SizePrefix.Kilo, SizeUnit.BYTE)

@Suppress("unused")
val Number.megabytes: Size
    get() = Size.of(this, SizePrefix.Mega, SizeUnit.BYTE)

@Suppress("unused")
val Number.gigabytes: Size
    get() = Size.of(this, SizePrefix.Giga, SizeUnit.BYTE)

@Suppress("unused")
val Number.terabytes: Size
    get() = Size.of(this, SizePrefix.Tera, SizeUnit.BYTE)

@Suppress("unused")
val Number.petabytes: Size
    get() = Size.of(this, SizePrefix.Peta, SizeUnit.BYTE)

@Suppress("unused")
val Number.exabytes: Size
    get() = Size.of(this, SizePrefix.Exa, SizeUnit.BYTE)

@Suppress("unused")
val Number.zettabytes: Size
    get() = Size.of(this, SizePrefix.Zetta, SizeUnit.BYTE)

@Suppress("unused")
val Number.yottabytes: Size
    get() = Size.of(this, SizePrefix.Yotta, SizeUnit.BYTE)

@Suppress("unused")
val Number.kibibytes: Size
    get() = Size.of(this, SizePrefix.Kibi, SizeUnit.BYTE)

@Suppress("unused")
val Number.mebibytes: Size
    get() = Size.of(this, SizePrefix.Mebi, SizeUnit.BYTE)

@Suppress("unused")
val Number.gibibytes: Size
    get() = Size.of(this, SizePrefix.Gibi, SizeUnit.BYTE)

@Suppress("unused")
val Number.tebibytes: Size
    get() = Size.of(this, SizePrefix.Tebi, SizeUnit.BYTE)

@Suppress("unused")
val Number.pebibytes: Size
    get() = Size.of(this, SizePrefix.Pebi, SizeUnit.BYTE)

@Suppress("unused")
val Number.exbibytes: Size
    get() = Size.of(this, SizePrefix.Exbi, SizeUnit.BYTE)

@Suppress("unused")
val Number.bits: Size
    get() = Size.of(this, null, SizeUnit.BIT)

@Suppress("unused")
val Number.kilobits: Size
    get() = Size.of(this, SizePrefix.Kilo, SizeUnit.BIT)

@Suppress("unused")
val Number.megabits: Size
    get() = Size.of(this, SizePrefix.Mega, SizeUnit.BIT)

@Suppress("unused")
val Number.gigabits: Size
    get() = Size.of(this, SizePrefix.Giga, SizeUnit.BIT)

@Suppress("unused")
val Number.terabits: Size
    get() = Size.of(this, SizePrefix.Tera, SizeUnit.BIT)

@Suppress("unused")
val Number.petabits: Size
    get() = Size.of(this, SizePrefix.Peta, SizeUnit.BIT)

@Suppress("unused")
val Number.exabits: Size
    get() = Size.of(this, SizePrefix.Exa, SizeUnit.BIT)

@Suppress("unused")
val Number.zettabits: Size
    get() = Size.of(this, SizePrefix.Zetta, SizeUnit.BIT)

@Suppress("unused")
val Number.yottabits: Size
    get() = Size.of(this, SizePrefix.Yotta, SizeUnit.BIT)

@Suppress("unused")
val Number.kibibits: Size
    get() = Size.of(this, SizePrefix.Kibi, SizeUnit.BIT)

@Suppress("unused")
val Number.mebibits: Size
    get() = Size.of(this, SizePrefix.Mebi, SizeUnit.BIT)

@Suppress("unused")
val Number.gibibits: Size
    get() = Size.of(this, SizePrefix.Gibi, SizeUnit.BIT)

@Suppress("unused")
val Number.tebibits: Size
    get() = Size.of(this, SizePrefix.Tebi, SizeUnit.BIT)

@Suppress("unused")
val Number.pebibits: Size
    get() = Size.of(this, SizePrefix.Pebi, SizeUnit.BIT)

@Suppress("unused")
val Number.exbibits: Size
    get() = Size.of(this, SizePrefix.Exbi, SizeUnit.BIT)

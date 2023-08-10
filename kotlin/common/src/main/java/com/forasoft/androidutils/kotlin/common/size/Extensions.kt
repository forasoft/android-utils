package com.forasoft.androidutils.kotlin.common.size


/**
 * Returns the [Size] equal to the sum of the sizes in the iterable
 */
public fun Iterable<Size>.sumOf(): Size = Size(this.sumOf { it.bitCount })

/**
 * Ensures that the size is not less than the passed [minimumValue]
 */
public fun Size.coerceAtLeast(minimumValue: Size): Size {
    return if (this < minimumValue) minimumValue else this
}

/**
 * Ensures that the size is not greater than the passed [maximumValue]
 */
public fun Size.coerceAtMost(maximumValue: Size): Size {
    return if (this > maximumValue) maximumValue else this
}

/**
 * Ensures that this value lies in the specified range [minimumValue]..[maximumValue]
 */
public fun Size.coerceIn(minimumValue: Size, maximumValue: Size): Size {
    require(minimumValue <= maximumValue) { "Lower bound can't be bigger than upper bound" }
    return when {
        this > maximumValue -> maximumValue
        this < minimumValue -> minimumValue
        else -> this
    }
}

public val Number.bytes: Size
    get() = Size.of(this, null, SizeUnit.BYTE)

public val Number.kilobytes: Size
    get() = Size.of(this, SizePrefix.Kilo, SizeUnit.BYTE)

public val Number.megabytes: Size
    get() = Size.of(this, SizePrefix.Mega, SizeUnit.BYTE)

public val Number.gigabytes: Size
    get() = Size.of(this, SizePrefix.Giga, SizeUnit.BYTE)

public val Number.terabytes: Size
    get() = Size.of(this, SizePrefix.Tera, SizeUnit.BYTE)

public val Number.petabytes: Size
    get() = Size.of(this, SizePrefix.Peta, SizeUnit.BYTE)

public val Number.exabytes: Size
    get() = Size.of(this, SizePrefix.Exa, SizeUnit.BYTE)

public val Number.zettabytes: Size
    get() = Size.of(this, SizePrefix.Zetta, SizeUnit.BYTE)

public val Number.yottabytes: Size
    get() = Size.of(this, SizePrefix.Yotta, SizeUnit.BYTE)

public val Number.kibibytes: Size
    get() = Size.of(this, SizePrefix.Kibi, SizeUnit.BYTE)

public val Number.mebibytes: Size
    get() = Size.of(this, SizePrefix.Mebi, SizeUnit.BYTE)

public val Number.gibibytes: Size
    get() = Size.of(this, SizePrefix.Gibi, SizeUnit.BYTE)

public val Number.tebibytes: Size
    get() = Size.of(this, SizePrefix.Tebi, SizeUnit.BYTE)

public val Number.pebibytes: Size
    get() = Size.of(this, SizePrefix.Pebi, SizeUnit.BYTE)

public val Number.exbibytes: Size
    get() = Size.of(this, SizePrefix.Exbi, SizeUnit.BYTE)

public val Number.bits: Size
    get() = Size.of(this, null, SizeUnit.BIT)

public val Number.kilobits: Size
    get() = Size.of(this, SizePrefix.Kilo, SizeUnit.BIT)

public val Number.megabits: Size
    get() = Size.of(this, SizePrefix.Mega, SizeUnit.BIT)

public val Number.gigabits: Size
    get() = Size.of(this, SizePrefix.Giga, SizeUnit.BIT)

public val Number.terabits: Size
    get() = Size.of(this, SizePrefix.Tera, SizeUnit.BIT)

public val Number.petabits: Size
    get() = Size.of(this, SizePrefix.Peta, SizeUnit.BIT)

public val Number.exabits: Size
    get() = Size.of(this, SizePrefix.Exa, SizeUnit.BIT)

public val Number.zettabits: Size
    get() = Size.of(this, SizePrefix.Zetta, SizeUnit.BIT)

public val Number.yottabits: Size
    get() = Size.of(this, SizePrefix.Yotta, SizeUnit.BIT)

public val Number.kibibits: Size
    get() = Size.of(this, SizePrefix.Kibi, SizeUnit.BIT)

public val Number.mebibits: Size
    get() = Size.of(this, SizePrefix.Mebi, SizeUnit.BIT)

public val Number.gibibits: Size
    get() = Size.of(this, SizePrefix.Gibi, SizeUnit.BIT)

public val Number.tebibits: Size
    get() = Size.of(this, SizePrefix.Tebi, SizeUnit.BIT)

public val Number.pebibits: Size
    get() = Size.of(this, SizePrefix.Pebi, SizeUnit.BIT)

public val Number.exbibits: Size
    get() = Size.of(this, SizePrefix.Exbi, SizeUnit.BIT)

package com.forasoft.androidutils.kotlin.common.size

import java.math.BigInteger

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

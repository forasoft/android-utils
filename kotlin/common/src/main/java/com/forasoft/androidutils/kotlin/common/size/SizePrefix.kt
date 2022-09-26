package com.forasoft.androidutils.kotlin.common.size

import com.forasoft.androidutils.kotlin.common.size.SizePrefix.BinaryPrefix
import com.forasoft.androidutils.kotlin.common.size.SizePrefix.Kibi
import com.forasoft.androidutils.kotlin.common.size.SizePrefix.Kilo
import com.forasoft.androidutils.kotlin.common.size.SizePrefix.Mebi
import com.forasoft.androidutils.kotlin.common.size.SizePrefix.Mega
import com.forasoft.androidutils.kotlin.common.size.SizePrefix.SiPrefix
import java.math.BigInteger

/**
 * Units of information multiplication prefix
 *
 * [SiPrefix]es are the prefixes from the SI system ([Kilo], [Mega], etc), having a multiplication base of 1000
 * [BinaryPrefix] are the prefixes based on the binary system ([Kibi], [Mebi], etc), having a multiplication base of 1024
 */
sealed interface SizePrefix {

    /**
     * Standard representation, used in the abbreviations
     */
    val abbreviation: String

    /**
     * Standard prefix for power, how many base units the prefix denotes
     */
    val multiplier: BigInteger

    /**
     * The prefix from the SI system ([Kilo], [Mega], etc), having a multiplication base of 1000
     */
    interface SiPrefix : SizePrefix

    /**
     * The prefixe based on the binary system ([Kibi], [Mebi], etc), having a multiplication base of 1024
     *
     * @constructor Create empty Binary prefix
     */
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

        /**
         * List of all [SiPrefix], sorted in ascending order
         */
        val siPrefixes
            get() = listOf(Kilo, Mega, Giga, Tera, Peta, Exa, Zetta, Yotta)

        /**
         * List of all [BinaryPrefix], sorted in ascending order
         */
        val binaryPrefixes
            get() = listOf(Kibi, Mebi, Gibi, Tebi, Pebi, Exbi)
    }

}

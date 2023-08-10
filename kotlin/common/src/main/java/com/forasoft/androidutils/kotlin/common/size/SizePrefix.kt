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
 * [SiPrefix]es are the prefixes from the SI system ([Kilo], [Mega], etc), having a
 * multiplication base of 1000
 *
 * [BinaryPrefix] are the prefixes based on the binary system ([Kibi], [Mebi], etc), having
 * a multiplication base of 1024
 */
@Suppress("MagicNumber")
public sealed interface SizePrefix {

    /**
     * Standard representation, used in the abbreviations
     */
    public val abbreviation: String

    /**
     * Standard prefix for power, how many base units the prefix denotes
     */
    public val multiplier: BigInteger

    /**
     * The prefix from the SI system ([Kilo], [Mega], etc), having a multiplication base of 1000
     */
    public interface SiPrefix : SizePrefix

    /**
     * The prefix based on the binary system ([Kibi], [Mebi], etc), having a multiplication base of 1024
     *
     * @constructor Create empty Binary prefix
     */
    public interface BinaryPrefix : SizePrefix

    public object Kilo : SiPrefix {
        override val multiplier: BigInteger = siMultiplier
        override val abbreviation: String = "k"
    }

    public object Mega : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(2)
        override val abbreviation: String = "M"
    }

    public object Giga : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(3)
        override val abbreviation: String = "G"
    }

    public object Tera : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(4)
        override val abbreviation: String = "T"
    }

    public object Peta : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(5)
        override val abbreviation: String = "P"
    }

    public object Exa : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(6)
        override val abbreviation: String = "E"
    }

    public object Zetta : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(7)
        override val abbreviation: String = "Z"
    }

    public object Yotta : SiPrefix {
        override val multiplier: BigInteger = siMultiplier.pow(8)
        override val abbreviation: String = "Y"
    }

    public object Kibi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier
        override val abbreviation: String = "Ki"
    }

    public object Mebi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(2)
        override val abbreviation: String = "Mi"
    }

    public object Gibi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(3)
        override val abbreviation: String = "Gi"
    }

    public object Tebi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(4)
        override val abbreviation: String = "Ti"
    }

    public object Pebi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(5)
        override val abbreviation: String = "Pi"
    }

    public object Exbi : BinaryPrefix {
        override val multiplier: BigInteger = binaryMultiplier.pow(6)
        override val abbreviation: String = "Ei"
    }

    public companion object {
        private val binaryMultiplier = 1024.toBigInteger()
        private val siMultiplier = 1000.toBigInteger()

        /**
         * List of all [SiPrefix], sorted in ascending order
         */
        public val siPrefixes: List<SiPrefix>
            get() = listOf(Kilo, Mega, Giga, Tera, Peta, Exa, Zetta, Yotta)

        /**
         * List of all [BinaryPrefix], sorted in ascending order
         */
        public val binaryPrefixes: List<BinaryPrefix>
            get() = listOf(Kibi, Mebi, Gibi, Tebi, Pebi, Exbi)
    }

}

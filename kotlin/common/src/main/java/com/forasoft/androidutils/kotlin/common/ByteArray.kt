package com.forasoft.androidutils.kotlin.common

/**
 * Converts [ByteArray] to a Hex string.
 */
fun ByteArray.toHexString(): String {
    return this.joinToString(separator = "") { byte -> "%02x".format(byte) }
}

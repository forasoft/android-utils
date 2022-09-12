package com.forasoft.androidutils.kotlin.common

/**
 * Converts [ByteArray] to a Hex string.
 *
 * Example:
 * ```
 * byteArrayOf(0, 1, 10, 100).toHexString()
 * > "00 01 0A 64"
 * ```
 *
 * @param separator [String] used as separator between converted bytes. Defaults to `" "`.
 */
fun ByteArray.toHexString(separator: String = " "): String {
    return this.joinToString(separator) { byte -> "%02X".format(byte) }
}

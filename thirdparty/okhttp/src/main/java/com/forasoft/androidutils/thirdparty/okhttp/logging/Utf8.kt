package com.forasoft.androidutils.thirdparty.okhttp.logging

import okio.Buffer
import java.io.EOFException

/**
 * Returns true if the body in question probably contains human readable text. Uses a small
 * sample of code points to detect unicode control characters commonly used in binary file
 * signatures.
 */
// Source: https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/main/kotlin/okhttp3/logging/internal/utf8.kt
@Suppress("MagicNumber", "ReturnCount")
internal fun Buffer.isProbablyUtf8(): Boolean {
    try {
        val prefix = Buffer()
        val byteCount = size.coerceAtMost(64)
        copyTo(prefix, 0, byteCount)
        for (i in 0 until 16) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        return true
    } catch (_: EOFException) {
        return false // Truncated UTF-8 sequence.
    }
}

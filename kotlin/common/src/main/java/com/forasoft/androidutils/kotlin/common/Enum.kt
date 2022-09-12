package com.forasoft.androidutils.kotlin.common

/**
 * Returns an enum entry with the specified name, or `null` if no such enum entry is found.
 */
@Suppress("SwallowedException")
inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
    return try {
        enumValueOf<T>(name)
    } catch (e: IllegalArgumentException) {
        null
    }
}

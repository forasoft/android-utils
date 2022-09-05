package com.forasoft.androidutils.kotlin.common.nullability

/**
 * Runs passed [operation] and returns its result, catching the expected [Throwable] of type [E]
 * and returning null instead.
 *
 * If any other throwable is thrown, it's rethrown.
 *
 * Primarily designed to make up for the lack of `xOrNull` APIs in Java.
 *
 * Example usage:
 * ```
 * val dateString = "asd"
 * val date = valueOrNull<DateTimeParseException, ZonedDateTime> { ZonedDateTime.parse(dateString) }
 * ```
 *
 * @param E allowed throwable type
 * @param R return type
 * @param operation function to run
 * @return [operation] result or null if [E] is thrown
 */
inline fun <R, reified E : Throwable> valueOrNull(operation: () -> R): R? {
    runCatching { operation() }
        .onSuccess { return it }
        .onFailure { if (it is E) return null else throw it }
    error("Result is neither success nor failure")
}

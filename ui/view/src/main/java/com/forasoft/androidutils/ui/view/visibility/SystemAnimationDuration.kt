package com.forasoft.androidutils.ui.view.visibility

import android.content.Context
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Represents animation durations specified in system resources.
 * The corresponding value can be retrieved with [Context.getSystemAnimationDuration] function.
 */
enum class SystemAnimationDuration {
    SHORT,
    MEDIUM,
    LONG,
}

/**
 * Returns [Duration] of the system animation specified in system resources.
 *
 * @param duration [SystemAnimationDuration] that represents the system animation duration
 * specified in system resources.
 */
fun Context.getSystemAnimationDuration(duration: SystemAnimationDuration): Duration {
    val resId = when (duration) {
        SystemAnimationDuration.SHORT -> android.R.integer.config_shortAnimTime
        SystemAnimationDuration.MEDIUM -> android.R.integer.config_mediumAnimTime
        SystemAnimationDuration.LONG -> android.R.integer.config_longAnimTime
    }
    return resources.getInteger(resId).milliseconds
}

package com.forasoft.androidutils.ui.view.visibility

import android.content.Context
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

enum class SystemAnimationDuration {
    SHORT,
    MEDIUM,
    LONG,
}

fun Context.getSystemAnimationDuration(duration: SystemAnimationDuration): Duration {
    val resId = when (duration) {
        SystemAnimationDuration.SHORT -> android.R.integer.config_shortAnimTime
        SystemAnimationDuration.MEDIUM -> android.R.integer.config_mediumAnimTime
        SystemAnimationDuration.LONG -> android.R.integer.config_longAnimTime
    }
    return resources.getInteger(resId).milliseconds
}

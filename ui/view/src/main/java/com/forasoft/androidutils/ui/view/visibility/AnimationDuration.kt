package com.forasoft.androidutils.ui.view.visibility

import android.content.Context
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

internal fun Context.getShortAnimationDuration(): Duration {
    return this.resources.getInteger(android.R.integer.config_shortAnimTime).milliseconds
}

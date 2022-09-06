package com.forasoft.androidutils.ui.view.visibility

import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import kotlin.time.Duration

// TODO: Write documentation
fun View.fade(
    isVisible: Boolean,
    duration: Duration = this.context.getShortAnimationDuration(),
    outTargetVisibility: Int = View.GONE,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
) {
    if (isVisible) {
        this.fadeIn(duration, interpolator)
    } else {
        this.fadeOut(duration, outTargetVisibility, interpolator)
    }
}

// TODO: Write documentation
fun View.fadeIn(
    duration: Duration = this.context.getShortAnimationDuration(),
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
) {
    this.animate()
        .apply { cancel() } // Cancel current animation
        .setInterpolator(interpolator)
        .setDuration(duration.inWholeMilliseconds)
        .withStartAction { isVisible = true }
        .alpha(1f)
}

// TODO: Write documentation
fun View.fadeOut(
    duration: Duration = this.context.getShortAnimationDuration(),
    targetVisibility: Int = View.GONE,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
) {
    this.animate()
        .apply { cancel() } // Cancel current animation
        .setInterpolator(interpolator)
        .setDuration(duration.inWholeMilliseconds)
        .withEndAction { visibility = targetVisibility }
        .alpha(0f)
}

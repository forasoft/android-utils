package com.forasoft.androidutils.ui.view.visibility

import android.animation.TimeInterpolator
import android.view.View
import androidx.core.view.isVisible
import kotlin.time.Duration

/**
 * Animates the appearance or disappearance of this [View] with fade effect depending on
 * the [isVisible] value using the current state of the View as the initial state.
 *
 * @param isVisible indicates whether the View should appear or disappear with the fade animation.
 * @param duration [Duration] of the animation. Defaults to
 * [android.R.integer.config_shortAnimTime] value.
 * @param fadeOutTargetVisibility visibility that will be set when the disappearing animation ends.
 * Defaults to [View.GONE].
 * @param interpolator [TimeInterpolator] that will be used by the underlying animator. Defaults
 * to `null` that results in linear interpolation.
 */
fun View.fade(
    isVisible: Boolean,
    duration: Duration = this.context.getShortAnimationDuration(),
    fadeOutTargetVisibility: Int = View.GONE,
    interpolator: TimeInterpolator? = null,
) {
    if (isVisible) {
        this.fadeIn(duration, interpolator)
    } else {
        this.fadeOut(duration, fadeOutTargetVisibility, interpolator)
    }
}

/**
 * Animates the appearance of this [View] with fade-in effect using the current state of the
 * View as the initial state.
 *
 * @param duration [Duration] of the animation. Defaults to
 * [android.R.integer.config_shortAnimTime] value.
 * @param interpolator [TimeInterpolator] that will be used by the underlying animator. Defaults
 * to `null` that results in linear interpolation.
 */
fun View.fadeIn(
    duration: Duration = this.context.getShortAnimationDuration(),
    interpolator: TimeInterpolator? = null,
) {
    this.animate()
        .apply { cancel() } // Cancel current animation
        .setInterpolator(interpolator)
        .setDuration(duration.inWholeMilliseconds)
        .withStartAction { isVisible = true }
        .alpha(1f)
}

/**
 * Animates the disappearance of this [View] with fade-out effect using the current state of the
 * View as the initial state.
 *
 * @param duration [Duration] of the animation. Defaults to
 * [android.R.integer.config_shortAnimTime] value.
 * @param targetVisibility visibility that will be set when the animation ends. Defaults to
 * [View.GONE].
 * @param interpolator [TimeInterpolator] that will be used by the underlying animator. Defaults
 * to `null` that results in linear interpolation.
 */
fun View.fadeOut(
    duration: Duration = this.context.getShortAnimationDuration(),
    targetVisibility: Int = View.GONE,
    interpolator: TimeInterpolator? = null,
) {
    this.animate()
        .apply { cancel() } // Cancel current animation
        .setInterpolator(interpolator)
        .setDuration(duration.inWholeMilliseconds)
        .withEndAction { visibility = targetVisibility }
        .alpha(0f)
}

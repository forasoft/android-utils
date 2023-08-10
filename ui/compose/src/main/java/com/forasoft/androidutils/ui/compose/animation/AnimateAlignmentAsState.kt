package com.forasoft.androidutils.ui.compose.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment

private val DefaultAnimation = spring<Float>()

/**
 * Fire-and-forget animation for [BiasAlignment]. When the provided targetAlignment is changed,
 * the animation will run automatically. If there is already an animation in-flight when
 * targetAlignment changes, the on-going animation will adjust course to animate towards
 * the new target value.
 *
 * @param targetAlignment target value of the animation.
 * @param animationSpec The animation that will be used to change the value through time. [spring]
 * will be used by default.
 * @param finishedListener An optional end listener to get notified when the animation is finished.
 * @return
 */
@Composable
public fun animateAlignmentAsState(
    targetAlignment: BiasAlignment,
    animationSpec: AnimationSpec<Float> = DefaultAnimation,
    visibilityThreshold: Float = 0.01f,
    label: String = "AlignmentAnimation",
    finishedListener: ((Float) -> Unit)? = null
): State<BiasAlignment> {
    val horizontalBias = animateFloatAsState(
        targetValue = targetAlignment.horizontalBias,
        animationSpec = animationSpec,
        visibilityThreshold = visibilityThreshold,
        label = label,
        finishedListener = finishedListener,
    )
    val verticalBias = animateFloatAsState(
        targetValue = targetAlignment.verticalBias,
        animationSpec = animationSpec,
        visibilityThreshold = visibilityThreshold,
        label = label,
        finishedListener = finishedListener,
    )
    return remember {
        derivedStateOf { BiasAlignment(horizontalBias.value, verticalBias.value) }
    }
}

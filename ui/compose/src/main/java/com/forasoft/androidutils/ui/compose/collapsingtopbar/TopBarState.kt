package com.forasoft.androidutils.ui.compose.collapsingtopbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection

// Source: androidx.compose.material3.TopAppBarState

/**
 * A state object that can be hoisted to control and observe the top app bar state. The state is
 * read and updated by a [TopBarScrollBehavior] implementation.
 *
 * In most cases, this state will be created via [rememberTopBarState].
 *
 * @param initialHeightOffsetLimit the initial value for [TopBarState.heightOffsetLimit]
 * @param initialHeightOffset the initial value for [TopBarState.heightOffset]
 * @param initialContentOffset the initial value for [TopBarState.contentOffset]
 */
@Stable
public class TopBarState(
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float,
    initialContentOffset: Float,
) {

    /**
     * The top app bar's height offset limit in pixels, which represents the limit that a top app
     * bar is allowed to collapse to.
     *
     * Use this limit to coerce the [heightOffset] value when it's updated.
     */
    public var heightOffsetLimit: Float by mutableFloatStateOf(initialHeightOffsetLimit)

    /**
     * The top app bar's current height offset in pixels. This height offset is applied to the fixed
     * height of the app bar to control the displayed height when content is being scrolled.
     *
     * Updates to the [heightOffset] value are coerced between zero and [heightOffsetLimit].
     */
    public var heightOffset: Float
        get() = _heightOffset.floatValue
        set(newOffset) {
            _heightOffset.floatValue = newOffset.coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f
            )
        }

    /**
     * The total offset of the content scrolled under the top app bar.
     *
     * The content offset is used to compute the [overlappedFraction], which can later be read
     * by an implementation.
     *
     * This value is updated by a [TopBarScrollBehavior] whenever a nested scroll connection
     * consumes scroll events. A common implementation would update the value to be the sum of all
     * [NestedScrollConnection.onPostScroll] `consumed.y` values.
     */
    public var contentOffset: Float by mutableFloatStateOf(initialContentOffset)

    /**
     * A value that represents the collapsed height percentage of the app bar.
     *
     * A `0.0` represents a fully expanded bar, and `1.0` represents a fully collapsed bar (computed
     * as [heightOffset] / [heightOffsetLimit]).
     */
    public val collapsedFraction: Float
        get() = if (heightOffsetLimit != 0f) {
            heightOffset / heightOffsetLimit
        } else {
            0f
        }

    /**
     * A value that represents the percentage of the app bar area that is overlapping with the
     * content scrolled behind it.
     *
     * A `0.0` indicates that the app bar does not overlap any content, while `1.0` indicates that
     * the entire visible app bar area overlaps the scrolled content.
     */
    public val overlappedFraction: Float
        get() = if (heightOffsetLimit != 0f) {
            1 - ((heightOffsetLimit - contentOffset).coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f
            ) / heightOffsetLimit)
        } else {
            0f
        }

    private var _heightOffset = mutableFloatStateOf(initialHeightOffset)

    public companion object {
        /**
         * The default [Saver] implementation for [TopBarState].
         */
        public val Saver: Saver<TopBarState, *> = listSaver(
            save = { listOf(it.heightOffsetLimit, it.heightOffset, it.contentOffset) },
            restore = {
                TopBarState(
                    initialHeightOffsetLimit = it[0],
                    initialHeightOffset = it[1],
                    initialContentOffset = it[2]
                )
            }
        )
    }

}

// Source: androidx.compose.material3.rememberTopAppBarState

/**
 * Creates a [TopBarState] that is remembered across compositions.
 *
 * @param initialHeightOffsetLimit the initial value for [TopBarState.heightOffsetLimit],
 * which represents the pixel limit that a top app bar is allowed to collapse when the scrollable
 * content is scrolled
 * @param initialHeightOffset the initial value for [TopBarState.heightOffset]. The initial
 * offset height offset should be between zero and [initialHeightOffsetLimit].
 * @param initialContentOffset the initial value for [TopBarState.contentOffset]
 */
@Composable
public fun rememberTopBarState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f
): TopBarState {
    return rememberSaveable(saver = TopBarState.Saver) {
        TopBarState(
            initialHeightOffsetLimit = initialHeightOffsetLimit,
            initialHeightOffset = initialHeightOffset,
            initialContentOffset = initialContentOffset,
        )
    }
}

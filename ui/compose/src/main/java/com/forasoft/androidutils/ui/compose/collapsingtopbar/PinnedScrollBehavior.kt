package com.forasoft.androidutils.ui.compose.collapsingtopbar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

// Source: androidx.compose.material3.PinnedScrollBehavior

/**
 * Returns a [TopBarScrollBehavior] that only adjusts its content offset, without adjusting any
 * properties that affect the height of a top app bar.
 *
 * @param state a [TopBarState]
 * @param canScroll a callback used to determine whether scroll events are to be
 * handled by this [PinnedScrollBehavior]
 */
internal class PinnedScrollBehavior(
    override val state: TopBarState,
    val canScroll: () -> Boolean = { true },
) : TopBarScrollBehavior {

    override val isPinned: Boolean = true

    override val snapAnimationSpec: AnimationSpec<Float>? = null

    override val flingAnimationSpec: DecayAnimationSpec<Float>? = null

    override var nestedScrollConnection = object : NestedScrollConnection {
        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            if (!canScroll()) return Offset.Zero
            if (consumed.y == 0f && available.y > 0f) {
                // Reset the total content offset to zero when scrolling all the way down.
                // This will eliminate some float precision inaccuracies.
                state.contentOffset = 0f
            } else {
                state.contentOffset += consumed.y
            }
            return Offset.Zero
        }
    }

}

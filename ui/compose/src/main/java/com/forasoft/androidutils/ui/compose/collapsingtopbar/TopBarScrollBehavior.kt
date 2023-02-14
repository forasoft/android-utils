package com.forasoft.androidutils.ui.compose.collapsingtopbar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection

// Source: androidx.compose.material3.TopAppBarScrollBehavior

/**
 * A TopBarScrollBehavior defines how an app bar should behave when the content under it is
 * scrolled.
 *
 * @see [TopBarDefaults.pinnedScrollBehavior]
 * @see [TopBarDefaults.enterAlwaysScrollBehavior]
 * @see [TopBarDefaults.exitUntilCollapsedScrollBehavior]
 */
@Stable
interface TopBarScrollBehavior {

    /**
     * A [TopBarState] that is attached to this behavior and is read and updated when scrolling
     * happens.
     */
    val state: TopBarState

    /**
     * Indicates whether the top app bar is pinned.
     *
     * A pinned app bar will stay fixed in place when content is scrolled and will not react to any
     * drag gestures.
     */
    val isPinned: Boolean

    /**
     * An optional [AnimationSpec] that defines how the top app bar snaps to either fully collapsed
     * or fully extended state when a fling or a drag scrolled it into an intermediate position.
     */
    val snapAnimationSpec: AnimationSpec<Float>?

    /**
     * An optional [DecayAnimationSpec] that defined how to fling the top app bar when the user
     * flings the app bar itself, or the content below it.
     */
    val flingAnimationSpec: DecayAnimationSpec<Float>?

    /**
     * A [NestedScrollConnection] that should be attached to a [Modifier.nestedScroll] in order to
     * keep track of the scroll events.
     */
    val nestedScrollConnection: NestedScrollConnection

}

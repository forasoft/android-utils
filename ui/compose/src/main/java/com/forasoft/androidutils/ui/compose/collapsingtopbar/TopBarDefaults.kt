package com.forasoft.androidutils.ui.compose.collapsingtopbar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

// Source: androidx.compose.material3.TopAppBarDefaults

/** Contains default values used for the top app bar implementations. */
public object TopBarDefaults {

    /**
     * Returns a pinned [TopBarScrollBehavior] that tracks nested-scroll callbacks and
     * updates its [TopBarState.contentOffset] accordingly.
     *
     * @param state the state object to be used to control or observe the top app bar's scroll
     * state. See [rememberTopBarState] for a state that is remembered across compositions.
     * @param canScroll a callback used to determine whether scroll events are to be handled by this
     * pinned [TopBarScrollBehavior]
     */
    @Composable
    public fun rememberPinnedScrollBehavior(
        state: TopBarState = rememberTopBarState(),
        canScroll: () -> Boolean = { true },
    ): TopBarScrollBehavior = remember(
        state,
        canScroll,
    ) {
        PinnedScrollBehavior(state = state, canScroll = canScroll)
    }

    /**
     * Returns a [TopBarScrollBehavior]. A top app bar that is set up with this
     * [TopBarScrollBehavior] will immediately collapse when the content is pulled up, and will
     * immediately appear when the content is pulled down.
     *
     * @param state the state object to be used to control or observe the top app bar's scroll
     * state. See [rememberTopBarState] for a state that is remembered across compositions.
     * @param canScroll a callback used to determine whether scroll events are to be
     * handled by this [EnterAlwaysScrollBehavior]
     * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top app bar snaps
     * to either fully collapsed or fully extended state when a fling or a drag scrolled it into an
     * intermediate position
     * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
     * app bar when the user flings the app bar itself, or the content below it
     */
    @Composable
    public fun rememberEnterAlwaysScrollBehavior(
        state: TopBarState = rememberTopBarState(),
        canScroll: () -> Boolean = { true },
        snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay(),
    ): TopBarScrollBehavior = remember(
        state,
        canScroll,
        snapAnimationSpec,
        flingAnimationSpec,
    ) {
        EnterAlwaysScrollBehavior(
            state = state,
            snapAnimationSpec = snapAnimationSpec,
            flingAnimationSpec = flingAnimationSpec,
            canScroll = canScroll,
        )
    }

    /**
     * Returns a [TopBarScrollBehavior] that adjusts its properties to affect the colors and
     * height of the top app bar.
     *
     * A top app bar that is set up with this [TopBarScrollBehavior] will immediately collapse
     * when the nested content is pulled up, and will expand back the collapsed area when the
     * content is  pulled all the way down.
     *
     * @param state the state object to be used to control or observe the top app bar's scroll
     * state. See [rememberTopBarState] for a state that is remembered across compositions.
     * @param canScroll a callback used to determine whether scroll events are to be
     * handled by this [ExitUntilCollapsedScrollBehavior]
     * @param snapAnimationSpec an optional [AnimationSpec] that defines how the top app bar snaps
     * to either fully collapsed or fully extended state when a fling or a drag scrolled it into an
     * intermediate position
     * @param flingAnimationSpec an optional [DecayAnimationSpec] that defined how to fling the top
     * app bar when the user flings the app bar itself, or the content below it
     */
    @Composable
    public fun rememberExitUntilCollapsedScrollBehavior(
        state: TopBarState = rememberTopBarState(),
        canScroll: () -> Boolean = { true },
        snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay(),
    ): TopBarScrollBehavior = remember(
        state,
        canScroll,
        snapAnimationSpec,
        flingAnimationSpec,
    ) {
        ExitUntilCollapsedScrollBehavior(
            state = state,
            snapAnimationSpec = snapAnimationSpec,
            flingAnimationSpec = flingAnimationSpec,
            canScroll = canScroll,
        )
    }

}

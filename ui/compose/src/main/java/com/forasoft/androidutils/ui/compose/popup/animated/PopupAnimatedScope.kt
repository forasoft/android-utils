package com.forasoft.androidutils.ui.compose.popup.animated

import androidx.compose.animation.core.Transition
import androidx.compose.runtime.Stable

/**
 * Receiver scope for content lambda for [PopupAnimated]. In this scope,
 * [transition] to add enter/exit transition for the content.
 */
@Stable
public interface PopupAnimatedScope {
    public val transition: Transition<Boolean>
}

@Stable
internal class PopupAnimatedScopeImpl(
    override val transition: Transition<Boolean>,
) : PopupAnimatedScope

package com.forasoft.androidutils.ui.compose.popup.animated

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties

/**
 * [Popup] wrapper that allows to add enter/exit animations for the content with
 * [PopupAnimatedScope] receiver scope. Use [isVisible] to control whether the popup is
 * visible or not.
 */
@Composable
public fun PopupAnimated(
    isVisible: Boolean,
    alignment: Alignment = Alignment.TopStart,
    offset: IntOffset = IntOffset.Zero,
    onDismissRequest: (() -> Unit)? = null,
    properties: PopupProperties = remember { PopupProperties() },
    content: @Composable PopupAnimatedScope.() -> Unit,
) {
    val transitionState = remember { MutableTransitionState(isVisible) }
    transitionState.targetState = isVisible

    if (transitionState.currentState || transitionState.targetState) {
        Popup(
            alignment = alignment,
            offset = offset,
            onDismissRequest = onDismissRequest,
            properties = properties,
        ) {
            val transition = updateTransition(
                transitionState = transitionState,
                label = Label,
            )
            val popupAnimatedScope = remember(transition) {
                PopupAnimatedScopeImpl(transition)
            }

            popupAnimatedScope.content()
        }
    }
}

/**
 * [Popup] wrapper that allows to add enter/exit animations for the content with
 * [PopupAnimatedScope] receiver scope. Use [isVisible] to control whether the popup is
 * visible or not.
 */
@Composable
public fun PopupAnimated(
    isVisible: Boolean,
    popupPositionProvider: PopupPositionProvider,
    onDismissRequest: (() -> Unit)? = null,
    properties: PopupProperties = remember { PopupProperties() },
    content: @Composable PopupAnimatedScope.() -> Unit,
) {
    val transitionState = remember { MutableTransitionState(isVisible) }
    transitionState.targetState = isVisible

    if (transitionState.currentState || transitionState.targetState) {
        Popup(
            popupPositionProvider = popupPositionProvider,
            onDismissRequest = onDismissRequest,
            properties = properties,
        ) {
            val transition = updateTransition(
                transitionState = transitionState,
                label = Label,
            )
            val popupAnimatedScope = remember(transition) {
                PopupAnimatedScopeImpl(transition)
            }

            popupAnimatedScope.content()
        }
    }
}

private const val Label = "PopupAnimated"

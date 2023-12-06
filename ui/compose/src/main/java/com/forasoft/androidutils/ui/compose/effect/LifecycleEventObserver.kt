package com.forasoft.androidutils.ui.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Effect for handling [Lifecycle] events.
 *
 * @param onEvent callback that will be invoked when a state transition event happens.
 */
@Composable
public fun LifecycleEventObserver(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val updatedOnEvent by rememberUpdatedState(onEvent)
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { owner, event ->
            updatedOnEvent(owner, event)
        }
        lifecycle.addObserver(observer)

        onDispose { lifecycle.removeObserver(observer) }
    }
}

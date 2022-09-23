@file:Suppress("Unused", "FunctionNaming")

package com.forasoft.androidutils.ui.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
fun LifecycleEventObserver(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, onEvent) {
        val observer = LifecycleEventObserver { owner, event ->
            onEvent(owner, event)
        }
        lifecycle.addObserver(observer)

        onDispose { lifecycle.removeObserver(observer) }
    }
}

package com.forasoft.androidutils.kotlin.coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Returns [StateFlow] containing the results of applying the given transform
 * function to each value of the original StateFlow.
 */
fun <T, R> StateFlow<T>.mapState(
    coroutineScope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(),
    transform: (T) -> R,
): StateFlow<R> {
    return this
        .map(transform)
        .stateIn(coroutineScope, started, transform(this.value))
}

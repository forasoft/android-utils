package com.forasoft.androidutils.ui.compose.behavior

import kotlinx.coroutines.flow.StateFlow

public interface BehaviorController<T : Behavior> {
    public val currentBehavior: StateFlow<T>

    public fun setDefaultBehavior(behavior: T)
    public fun pushBehavior(behavior: T)
    public fun popBehavior(behavior: T)
}

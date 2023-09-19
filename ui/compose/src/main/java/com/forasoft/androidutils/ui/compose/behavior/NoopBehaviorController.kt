package com.forasoft.androidutils.ui.compose.behavior

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

public class NoopBehaviorController<T : Behavior>(defaultBehavior: T) : BehaviorController<T> {
    override val currentBehavior: StateFlow<T> = MutableStateFlow(defaultBehavior)

    override fun setDefaultBehavior(behavior: T): Unit = Unit

    override fun pushBehavior(behavior: T): Unit = Unit

    override fun popBehavior(behavior: T): Unit = Unit
}

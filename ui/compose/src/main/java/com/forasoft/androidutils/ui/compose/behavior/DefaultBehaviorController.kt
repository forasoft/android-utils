package com.forasoft.androidutils.ui.compose.behavior

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * An implementation of [BehaviorController] that manages [Behavior] in a stack.
 *
 * @param defaultBehavior [Behavior] used by default
 */
public open class DefaultBehaviorController<T : Behavior>(
    private var defaultBehavior: T,
) : BehaviorController<T> {
    private val behaviorStack = ArrayDeque<T>()

    private val _currentBehavior = MutableStateFlow(defaultBehavior)
    override val currentBehavior: StateFlow<T> = _currentBehavior.asStateFlow()

    @Synchronized
    override fun setDefaultBehavior(behavior: T) {
        defaultBehavior = behavior
        updateCurrentBehavior()
    }

    override fun pushBehavior(behavior: T) {
        behaviorStack.addFirst(behavior)
        updateCurrentBehavior()
    }

    override fun popBehavior(behavior: T) {
        val lastIndex = behaviorStack.indexOfLast { it == behavior }
        behaviorStack.removeAt(lastIndex)
        updateCurrentBehavior()
    }

    private fun updateCurrentBehavior() {
        _currentBehavior.value = behaviorStack.firstOrNull() ?: defaultBehavior
    }
}

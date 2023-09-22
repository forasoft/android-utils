package com.forasoft.androidutils.ui.compose.behavior

import kotlinx.coroutines.flow.StateFlow

/**
 * An interface that describes the logic of behavior management.
 *
 * Designed to control the current [Behavior].
 *
 * @param T defines the type of behavior to be used.
 */
public interface BehaviorController<T : Behavior> {
    /**
     * The current [Behavior], represented as a [StateFlow].
     */
    public val currentBehavior: StateFlow<T>

    /**
     * A method to set the default behavior.
     *
     * @param behavior [Behavior] to be set as the default.
     */
    public fun setDefaultBehavior(behavior: T)

    /**
     * A method to add a behavior to the stack.
     *
     * @param behavior [Behavior] to add to the stack.
     */
    public fun pushBehavior(behavior: T)

    /**
     * A method to remove a behavior from the stack.
     *
     * @param behavior [Behavior] to remove from the stack.
     */
    public fun popBehavior(behavior: T)
}

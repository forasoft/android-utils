package com.forasoft.androidutils.permissionmanager

/**
 * Represents the state of a permission.
 */
public sealed interface PermissionState {
    /**
     * Represents the state of the granted permission.
     */
    public data object Granted : PermissionState

    /**
     * Represents the state of the denied permission. Use [shouldShowRequestRationale] to check
     * it is needed to show permission request rationale to the user.
     */
    public data class Denied(val shouldShowRequestRationale: Boolean) : PermissionState

    /**
     * Returns `true` if this instance represents the [PermissionState.Granted] state.
     */
    public val isGranted: Boolean
        get() = this == Granted

    /**
     * Returns `true` if this instance represents the [PermissionState.Denied] state.
     */
    public val isDenied: Boolean
        get() = this is Denied
}

/**
 * Performs the given [action] if this instance represents the [PermissionState.Granted] state.
 * Returns the original [PermissionState] unchanged.
 */
public inline fun PermissionState.onGranted(action: () -> Unit): PermissionState {
    if (isGranted) action()
    return this
}

/**
 * Performs the given [action] if this instance represents the [PermissionState.Denied] state.
 * Returns the original [PermissionState] unchanged.
 */
public inline fun PermissionState.onDenied(
    action: (shouldShowRequestRationale: Boolean) -> Unit,
): PermissionState {
    if (this is PermissionState.Denied) {
        action(shouldShowRequestRationale)
    }
    return this
}

package com.forasoft.androidutils.permissionmanager

import android.content.Context
import androidx.activity.ComponentActivity
import com.forasoft.androidutils.permissionmanager.PermissionManager.Companion.create
import kotlinx.coroutines.flow.Flow

/**
 * A wrapper around Android permissions that allows to work with permissions outside of the
 * UI layer. [PermissionState] is used to describe the state of a permission.
 *
 * PermissionManager also tracks and stores the `shouldShowRequestPermissionRationale` state
 * of each permission that was requested or checked using PermissionManager.
 * This allows to make *an assumption* whether the permission was permanently denied by the user.
 *
 * Use [create] method to create a new instance.
 */
public interface PermissionManager {
    /**
     * Request permission.
     */
    public suspend fun requestPermission(permission: String): PermissionState

    /**
     * Request multiple permissions.
     */
    public suspend fun requestPermissions(permissions: List<String>): Map<String, PermissionState>

    /**
     * Get [PermissionState] of the given permission.
     */
    public fun getPermissionState(permission: String): PermissionState

    /**
     * Get [PermissionState]s of the given permissions.
     */
    public fun getPermissionsState(permissions: List<String>): Map<String, PermissionState>

    /**
     * Check if the permission has required request rationale in past.
     */
    public fun hasPermissionRequiredRequestRationale(permission: String): Flow<Boolean?>

    /**
     * Check if the permissions have required request rationale in past.
     */
    public fun havePermissionsRequiredRequestRationale(
        permissions: List<String>,
    ): Flow<Map<String, Boolean?>>

    /**
     * Set Activity that will be used to request permissions under the hood.
     *
     * @see [unsetActivity]
     */
    public fun setActivity(activity: ComponentActivity)

    /**
     * Unset Activity. This method does nothing if the [activity] is not set at the moment.
     *
     * @see [setActivity]
     */
    public fun unsetActivity(activity: ComponentActivity)

    /**
     * Release resources.
     *
     * Consider using [unsetActivity] if it is needed to unset an Activity before setting a new one.
     *
     * @see [unsetActivity]
     */
    public fun release()

    public companion object {
        /**
         * Create a new instance of [PermissionManager].
         */
        public fun create(context: Context): PermissionManager {
            return PermissionManagerImpl(context)
        }
    }
}

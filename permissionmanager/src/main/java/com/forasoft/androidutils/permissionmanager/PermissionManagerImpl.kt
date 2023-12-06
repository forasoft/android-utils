package com.forasoft.androidutils.permissionmanager

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import kotlin.coroutines.resume

internal class PermissionManagerImpl(context: Context) : PermissionManager {
    private val storage = PermissionManagerStorage(context)

    private var activity: ComponentActivity? = null

    override suspend fun requestPermission(permission: String): PermissionState {
        val activity = checkNotNull(activity) { ACTIVITY_NULL_ERROR_MESSAGE }
        var launcher: ActivityResultLauncher<String>? = null
        suspendCancellableCoroutine { continuation ->
            launcher = activity.activityResultRegistry.register(
                UUID.randomUUID().toString(),
                ActivityResultContracts.RequestPermission(),
            ) {
                continuation.resume(Unit)
            }
            launcher?.launch(permission)

            continuation.invokeOnCancellation {
                launcher?.unregister()
            }
        }
        launcher?.unregister()

        if (shouldShowRequestPermissionRationale(permission)) {
            storage.savePermissionRequiredRequestRationale(permission)
        }

        return getPermissionState(permission)
    }

    override suspend fun requestPermissions(
        permissions: List<String>,
    ): Map<String, PermissionState> {
        val activity = checkNotNull(activity) { ACTIVITY_NULL_ERROR_MESSAGE }
        var launcher: ActivityResultLauncher<Array<String>>? = null
        suspendCancellableCoroutine { continuation ->
            launcher = activity.activityResultRegistry.register(
                UUID.randomUUID().toString(),
                ActivityResultContracts.RequestMultiplePermissions(),
            ) {
                continuation.resume(Unit)
            }
            launcher?.launch(permissions.toTypedArray())

            continuation.invokeOnCancellation {
                launcher?.unregister()
            }
        }
        launcher?.unregister()

        for (permission in permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                storage.savePermissionRequiredRequestRationale(permission)
            }
        }

        return getPermissionsState(permissions)
    }

    override fun getPermissionState(permission: String): PermissionState {
        return if (isPermissionGranted(permission)) {
            PermissionState.Granted
        } else {
            val shouldShowRequestRationale = shouldShowRequestPermissionRationale(permission)
            PermissionState.Denied(shouldShowRequestRationale)
        }
    }

    override fun getPermissionsState(permissions: List<String>): Map<String, PermissionState> {
        val map = mutableMapOf<String, PermissionState>()
        for (permission in permissions) {
            map[permission] = getPermissionState(permission)
        }
        return map
    }

    override fun hasPermissionRequiredRequestRationale(permission: String): Flow<Boolean?> {
        return storage.hasPermissionRequiredRequestRationale(permission)
    }

    override fun havePermissionsRequiredRequestRationale(
        permissions: List<String>,
    ): Flow<Map<String, Boolean?>> {
        return storage.havePermissionsRequiredRequestRationale(permissions)
    }

    @Synchronized
    override fun setActivity(activity: ComponentActivity) {
        this.activity = activity
    }

    @Synchronized
    override fun unsetActivity(activity: ComponentActivity) {
        if (this.activity == activity) {
            this.activity = null
        }
    }

    @Synchronized
    override fun release() {
        activity = null
    }

    private fun isPermissionGranted(permission: String): Boolean {
        val activity = checkNotNull(activity) { ACTIVITY_NULL_ERROR_MESSAGE }
        return ContextCompat.checkSelfPermission(
            activity,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        val activity = checkNotNull(activity) { ACTIVITY_NULL_ERROR_MESSAGE }
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    companion object {
        private const val ACTIVITY_NULL_ERROR_MESSAGE =
            "Activity can not be null. Did you forget to call setActivity?"
    }
}

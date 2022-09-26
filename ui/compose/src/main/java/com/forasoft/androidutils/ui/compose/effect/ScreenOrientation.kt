@file:Suppress("Unused", "FunctionNaming")

package com.forasoft.androidutils.ui.compose.effect

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.forasoft.androidutils.platform.android.getActivity

/**
 * Effect for screen orientation setup. When the effect leaves the composition, the
 * previous screen orientation is restored.
 *
 * @param orientation one of [ActivityInfo.screenOrientation] constants.
 */
@Composable
fun ScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(context, orientation) {
        val activity = context.getActivity() ?: return@DisposableEffect onDispose {}
        val previousOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            activity.requestedOrientation = previousOrientation
        }
    }
}

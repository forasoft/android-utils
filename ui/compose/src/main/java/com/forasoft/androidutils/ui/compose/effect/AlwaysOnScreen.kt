@file:Suppress("Unused", "FunctionNaming")

package com.forasoft.androidutils.ui.compose.effect

import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.forasoft.androidutils.platform.android.getActivity

/**
 * Effect for keeping the screen on. When the effect leaves the composition, the screen
 * is no longer kept turned on.
 */
@Composable
fun AlwaysOnScreen() {
    val context = LocalContext.current
    DisposableEffect(context) {
        val window = context.getActivity()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}

package com.forasoft.androidutils.ui.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.forasoft.androidutils.platform.android.getActivity

/**
 * Effect for hiding system bars. When the effect leaves the composition, the system bars
 * show up and the previous behavior is restored.
 *
 * @param behavior determines how the bars behave when being hidden.
 * Should be one of [WindowInsetsControllerCompat] behavior constants.
 */
@Suppress("Unused", "FunctionNaming")
@Composable
fun HiddenSystemBars(
    behavior: Int = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE,
) {
    val view = LocalView.current
    val activity = LocalContext.current.getActivity()
    val window = activity?.window

    DisposableEffect(view, window) {
        if (window != null) {
            val insetsController = WindowCompat.getInsetsController(window, view)

            val previousSystemBarsBehavior = insetsController.systemBarsBehavior

            insetsController.hide(WindowInsetsCompat.Type.systemBars())
            insetsController.systemBarsBehavior = behavior

            onDispose {
                insetsController.show(WindowInsetsCompat.Type.systemBars())
                insetsController.systemBarsBehavior = previousSystemBarsBehavior
            }
        } else {
            onDispose {  }
        }
    }
}

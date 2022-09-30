package com.forasoft.androidutils.platform.android.toastlogger

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.ref.WeakReference

/**
 * Displays logged messages with [Toast]s.
 *
 * This class was designed to be used in conjunction with [TimberUiTree].
 *
 * @see TimberUiTree
 */
class ToastLogger(private val context: Context) {

    private val handler = Handler(Looper.getMainLooper())

    private var currentToast: WeakReference<Toast>? = null

    fun log(message: String) {
        handler.post {
            currentToast?.get()?.cancel()
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            currentToast = WeakReference(toast)
            toast.show()
        }
    }

}

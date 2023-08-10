package com.forasoft.androidutils.platform.android

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.ref.WeakReference

/**
 * Displays messages with [Toast]s.
 */
public class ToastLogger(context: Context) {

    private val contextRef = WeakReference(context)

    private val handler = Handler(Looper.getMainLooper())

    private var currentToastRef: WeakReference<Toast>? = null

    public fun log(message: String) {
        handler.post {
            val context = contextRef.get() ?: return@post
            currentToastRef?.get()?.cancel()
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            currentToastRef = WeakReference(toast)
            toast.show()
        }
    }

}

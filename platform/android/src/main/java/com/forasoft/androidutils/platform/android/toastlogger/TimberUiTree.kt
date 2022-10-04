package com.forasoft.androidutils.platform.android.toastlogger

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.forasoft.androidutils.platform.android.ToastLogger
import timber.log.Timber

/**
 * A [Timber.Tree] that displays all [Timber.d] logs with [Toast]s.
 *
 * Usage:
 * ```
 * class MyApplication : Application() {
 *     override fun onCreate() {
 *         super.onCreate()
 *         if (isUiLoggingEnabled) Timber.plant(TimberUiTree(this))
 *     }
 * }
 * ```
 *
 * @property toastLogger displays messages as Toasts.
 */
@Suppress("Unused")
class TimberUiTree(private val toastLogger: ToastLogger) : Timber.Tree() {

    /**
     * Shortcut for creating [TimberUiTree] with a [ToastLogger] initialized from the
     * given [Context].
     */
    constructor(context: Context) : this(ToastLogger(context))

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.DEBUG) toastLogger.log(message)
    }

}

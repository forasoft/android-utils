package com.forasoft.androidutils.logpecker

import android.content.Context
import androidx.startup.Initializer

@Suppress("Unused")
internal class LogPeckerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val isEnabled =
            context.resources.getBoolean(R.bool.forasoftandroidutils_log_pecker_enabled)
        if (isEnabled) {
            val logPecker = LogPecker(context)
            logPecker.start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}

package com.forasoft.androidutils.logpecker

import android.content.Context
import androidx.startup.Initializer
import com.forasoft.androidutils.logpecker.ui.addLogPeckerDynamicShortcut

/**
 * Initializes LogPecker at application startup.
 */
internal class LogPeckerInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val isEnabled =
            context.resources.getBoolean(R.bool.forasoftandroidutils_log_pecker_is_enabled)
        if (isEnabled) {
            val logPecker = LogPecker(context)
            logPecker.start()

            addLogPeckerDynamicShortcut(context)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}

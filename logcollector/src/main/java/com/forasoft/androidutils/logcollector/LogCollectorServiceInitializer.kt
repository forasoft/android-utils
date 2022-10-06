package com.forasoft.androidutils.logcollector

import android.content.Context
import androidx.startup.Initializer

@Suppress("Unused")
class LogCollectorServiceInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val isEnabled = context.resources.getBoolean(R.bool.log_collector_is_enabled)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}

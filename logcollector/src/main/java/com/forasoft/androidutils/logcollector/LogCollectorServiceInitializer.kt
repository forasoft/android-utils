package com.forasoft.androidutils.logcollector

import android.content.Context
import android.content.Intent
import androidx.startup.Initializer

@Suppress("Unused")
class LogCollectorServiceInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val intent = Intent(context, LogCollectorService::class.java)
        context.startService(intent)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}

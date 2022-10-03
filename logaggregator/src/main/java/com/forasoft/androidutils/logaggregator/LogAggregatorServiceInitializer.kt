package com.forasoft.androidutils.logaggregator

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.startup.Initializer

@Suppress("Unused")
class LogAggregatorServiceInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val intent = Intent(context, LogAggregatorService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}

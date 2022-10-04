package com.forasoft.androidutils.logcollector

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class LogCollectorService : Service() {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    private val logCollector by lazy { LogCollector(this) }

    // TODO
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_STICKY

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Timber.v("LogCollectorService created")
        coroutineScope.launch { logCollector.start() }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("LogCollectorService destroyed")
        coroutineScope.cancel()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Timber.v("onTaskRemoved")
        stopSelf()
    }

}

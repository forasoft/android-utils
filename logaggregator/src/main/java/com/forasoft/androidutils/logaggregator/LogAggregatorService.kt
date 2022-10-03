package com.forasoft.androidutils.logaggregator

import android.app.Service
import android.content.Intent
import android.os.IBinder
import timber.log.Timber

class LogAggregatorService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_NOT_STICKY

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Timber.v("LogAggregatorService created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("LogAggregatorService destroyed")
    }

}

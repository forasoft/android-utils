package com.forasoft.androidutils.logcollector

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class LogCollectorService : Service() {

    private val notificationManager by lazy {
        NotificationManagerCompat.from(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = START_NOT_STICKY

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Timber.v("LogCollectorService created")

        startForeground()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("LogCollectorService destroyed")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Timber.v("onTaskRemoved")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        stopSelf()
    }

    private fun startForeground() {
        createNotificationChannel()
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val importance = NotificationManagerCompat.IMPORTANCE_LOW
        val id = getString(R.string.log_collector_notification_channel_id)
        val name = getString(R.string.log_collector_notification_channel_name)
        val channel = NotificationChannelCompat.Builder(id, importance)
            .setName(name)
            .setLightsEnabled(false)
            .setVibrationEnabled(false)
            .build()
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val channelId = getString(R.string.log_collector_notification_channel_id)
        val title = getString(R.string.log_collector_notification_title)
        val appName = getString(applicationInfo.labelRes)
        val body = getString(R.string.log_collector_notification_body, appName)
        return NotificationCompat.Builder(this, channelId)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(android.R.drawable.ic_menu_save) // TODO: Change icon
            .setContentTitle(title)
            .setContentText(body)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 145543233
    }

}

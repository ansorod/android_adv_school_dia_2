package com.ansorod.simplealarm.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ansorod.simplealarm.R

object NotificationHelper {

    private const val NOTIFICATION_CHANNEL_ALARM_ID = "notification_channel_alarm"

    private fun createBaseChannel(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.notification_alarm_channel_name)
            val channelDescription = context.getString(R.string.notification_alarm_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ALARM_ID, channelName, importance).apply {
                description = channelDescription
                enableVibration(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun notifyAlarm(context: Context, message: String) {

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ALARM_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notification_alarm_title))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle())
            .setAutoCancel(true)

        createBaseChannel(context)
        NotificationManagerCompat.from(context).notify(0, builder.build())
    }
}
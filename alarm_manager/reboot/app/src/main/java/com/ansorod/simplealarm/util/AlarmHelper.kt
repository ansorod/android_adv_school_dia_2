package com.ansorod.simplealarm.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ansorod.simplealarm.receiver.AlarmBroadcastReceiver

object AlarmHelper {

    fun scheduleRTC(context: Context, message: String, futureTime: Long) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra(AlarmBroadcastReceiver.EXTRA_MESSAGE, message)

        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_ONE_SHOT)

        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.setExact(AlarmManager.RTC_WAKEUP, futureTime, pendingIntent)

        SharedPreferencesHelper.storeAlarm(context, futureTime, message)
    }
}
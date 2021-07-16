package com.ansorod.simplealarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ansorod.simplealarm.util.AlarmHelper
import com.ansorod.simplealarm.util.NotificationHelper
import com.ansorod.simplealarm.util.SharedPreferencesHelper
import java.util.*

class BootCompletedBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("jamal", "BootCompleted");
        context?.let {
            val timestamp = SharedPreferencesHelper.loadTimestamp(context)
            val message = SharedPreferencesHelper.loadMessage(context)

            if(timestamp != -1L) {
                if(timestamp < Calendar.getInstance().timeInMillis) {
                    Log.i("jamal", "notify expired Alarm")
                    NotificationHelper.notifyAlarm(it, message)
                } else {
                    Log.i("jamal", "re-schedule Alarm")
                    AlarmHelper.scheduleRTC(it, message, timestamp)
                }
            }
        }
    }
}
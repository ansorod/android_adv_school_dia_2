package com.ansorod.simplealarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.ansorod.simplealarm.util.NotificationHelper
import com.ansorod.simplealarm.util.SharedPreferencesHelper

class AlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("jamal", "Received broadcast from AlarmManager")
        context?.let {
            NotificationHelper.notifyAlarm(it, intent?.getStringExtra(EXTRA_MESSAGE) ?: "")
            SharedPreferencesHelper.clear(it)
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
    }
}
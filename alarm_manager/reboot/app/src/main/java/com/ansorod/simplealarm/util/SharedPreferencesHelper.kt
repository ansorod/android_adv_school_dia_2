package com.ansorod.simplealarm.util

import android.content.Context

object SharedPreferencesHelper {

    private const val PREFS_NAME = "AlarmManagerPrefs"
    private const val KEY_TIMESTAMP = "com.ansorod.simplealarm.KEY_TIMESTAMP"
    private const val KEY_MSG = "com.ansorod.simplealarm.KEY_MSG"

    fun storeAlarm(context: Context, timestamp: Long, message: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        prefs.putLong(KEY_TIMESTAMP, timestamp).apply()
        prefs.putString(KEY_MSG, message).apply()
    }

    fun loadTimestamp(context: Context): Long {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getLong(
            KEY_TIMESTAMP, -1)
    }

    fun loadMessage(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString(
            KEY_MSG, "") ?: ""
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear()
    }
}
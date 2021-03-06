package com.ansorod.simplealarm.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import com.ansorod.simplealarm.R
import com.ansorod.simplealarm.receiver.AlarmBroadcastReceiver
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        Log.i("jamal", "Epoch time: ${System.currentTimeMillis()}")
        Log.i("jamal", "System time: ${SystemClock.elapsedRealtime()}")
    }

    private fun initView() {

        elapsedTimeButton.setOnClickListener {
            Log.i("jamal", "scheduleElapsedTimed")
            scheduleElapsedTime()
        }

        realTimeButton.setOnClickListener {
            Log.i("jamal", "scheduleRTC")
            scheduleRTC()
        }
    }

    private fun scheduleElapsedTime() {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val manager = getAlarmManager()
        manager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 30000, pendingIntent)
    }

    private fun scheduleRTC() {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        val futureTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 4)
        }

        val manager = getAlarmManager()
        manager.set(AlarmManager.RTC, futureTime.timeInMillis, pendingIntent)

        manager.setRepeating(AlarmManager.RTC_WAKEUP, futureTime.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, futureTime.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun getAlarmManager(): AlarmManager {
        return getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun cancel() {
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        val cancelPending = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        getAlarmManager().cancel(cancelPending)

    }

}
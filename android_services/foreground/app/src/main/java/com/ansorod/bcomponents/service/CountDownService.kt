package com.ansorod.bcomponents.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ansorod.bcomponents.NotificationHelper

class CountDownService: Service() {

    private var shouldStopTimers = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(112, NotificationHelper.getForegroundNotification(this))

        if(intent == null) {
            stopForeground(0)
        }

        intent?.let { receivedIntent ->
            val countdownTime = receivedIntent.getIntExtra(EXTRA_COUNTDOWN_TIME, 0)
            countDown(countdownTime)
        }

        return START_STICKY
    }

    private fun countDown(countDownTime: Int) {
        if(countDownTime <= 0) {
            return
        }

        val MILLISECOND = 1000L

        val countDownTimer: CountDownTimer = object : CountDownTimer(countDownTime * MILLISECOND, MILLISECOND) {
            override fun onTick(millisUntilFinished: Long) {
                if(shouldStopTimers) {
                    this.cancel()
                }

                reportCurrentTime((millisUntilFinished / MILLISECOND).toInt())
            }

            override fun onFinish() {
                this.cancel()
            }
        }

        countDownTimer.start()
    }

    private fun countDownSync(countDownTime: Int) {
        var count = countDownTime
        while(count > 0) {
            Thread.sleep(1000)
            reportCurrentTime(count)
            count--
        }
    }

    private fun reportCurrentTime(untilFinished: Int) {
        val intent = Intent(ACTION_CURRENT_COUNT)
        intent.putExtra(EXTRA_CURRENT_COUNT, untilFinished)

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("jamal", "onDestroy")
        shouldStopTimers = true
        NotificationHelper.cancelForegroundNotification(this)
        stopForeground(0)
    }

    companion object {
        const val ACTION_CURRENT_COUNT = "com.ansorod.bcomponents.service.CountDownService.ACTION_CURRENT_COUNT"
        const val EXTRA_CURRENT_COUNT = "com.ansorod.bcomponents.service.CountDownService.EXTRA_CURRENT_COUNT"
        private const val EXTRA_COUNTDOWN_TIME = "EXTRA_COUNTDOWN_TIME"

        fun startCountDown(context: Context, countDownTime: Int) {
            val intent = Intent(context, CountDownService::class.java)
            intent.putExtra(EXTRA_COUNTDOWN_TIME, countDownTime)
            ContextCompat.startForegroundService(context, intent)
        }

        fun stopCountDown(context: Context) {
            val intent = Intent(context, CountDownService::class.java)
            context.stopService(intent)
        }
    }

}
package com.ansorod.bcomponents.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class CountDownService: Service() {

    private var shouldStopTimers = false

    private val binder = CountdownBinder()
    private var listener: CountdownListener? = null

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun setCountdownListener(countdownListener: CountdownListener?) {
        listener = countdownListener
    }

    fun countDown(countDownTime: Int) {
        if(countDownTime <= 0) {
            return
        }

        shouldStopTimers = false

        val MILLISECOND = 1000L

        val countDownTimer: CountDownTimer = object : CountDownTimer(countDownTime * MILLISECOND, MILLISECOND) {
            override fun onTick(millisUntilFinished: Long) {
                if(shouldStopTimers) {
                    this.cancel()
                }

                reportCurrentTime((millisUntilFinished / MILLISECOND).toInt())
            }

            override fun onFinish() {
            }
        }

        countDownTimer.start()
    }

    fun stopCountdown() {
        shouldStopTimers = true
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
        listener?.onTick(untilFinished)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("jamal", "onDestroy")
        shouldStopTimers = true
        stopSelf()
    }

    inner class CountdownBinder: Binder() {
        fun getService(): CountDownService = this@CountDownService
    }

    interface CountdownListener {
        fun onTick(currentTime: Int)
    }
}
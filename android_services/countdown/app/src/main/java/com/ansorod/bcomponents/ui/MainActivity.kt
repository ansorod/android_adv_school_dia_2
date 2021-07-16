package com.ansorod.bcomponents.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ansorod.bcomponents.R
import com.ansorod.bcomponents.service.CountDownService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {

        startCountdownButton.setOnClickListener {
            val countDownValue = countdownInput.text.toString().toInt()
            currentTimeTextView.text = countDownValue.toString()
            CountDownService.startCountDown(this, countDownValue)
        }

        stopCountdownButton.setOnClickListener {
            CountDownService.stopCountDown(this)
        }
    }

    private val countDownReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                val currentCount = intent.getIntExtra(CountDownService.EXTRA_CURRENT_COUNT, 0)
                currentTimeTextView.text = currentCount.toString()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(countDownReceiver)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(CountDownService.ACTION_CURRENT_COUNT)
        LocalBroadcastManager.getInstance(this).registerReceiver(countDownReceiver, filter)
    }
}
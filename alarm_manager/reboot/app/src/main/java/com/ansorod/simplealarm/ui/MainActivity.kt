package com.ansorod.simplealarm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ansorod.simplealarm.R
import com.ansorod.simplealarm.util.AlarmHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        setReminderButton.setOnClickListener {

            val futureTime = Calendar.getInstance().apply {
                add(Calendar.MINUTE, 5)
            }

            AlarmHelper.scheduleRTC(this, reminderTextView.text.toString(), futureTime.timeInMillis)
        }
    }
}
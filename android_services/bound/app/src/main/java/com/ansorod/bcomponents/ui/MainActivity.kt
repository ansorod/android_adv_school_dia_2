package com.ansorod.bcomponents.ui

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ansorod.bcomponents.R
import com.ansorod.bcomponents.service.CountDownService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCountDownService: CountDownService? = null

    private val mConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as CountDownService.CountdownBinder
            mCountDownService = binder.getService()
            mCountDownService?.setCountdownListener(countDownListener)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mCountDownService = null
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {

        startCountdownButton.setOnClickListener {
            val countDownValue = countdownInput.text.toString().toInt()
            currentTimeTextView.text = countDownValue.toString()
            mCountDownService?.countDown(countDownValue)
        }

        stopCountdownButton.setOnClickListener {
            mCountDownService?.stopCountdown()
        }
    }


    private val countDownListener: CountDownService.CountdownListener = object: CountDownService.CountdownListener {
        override fun onTick(currentTime: Int) {
            currentTimeTextView.text = currentTime.toString()
        }
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this, CountDownService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()

        mCountDownService?.setCountdownListener(null)
        unbindService(mConnection)
    }

}
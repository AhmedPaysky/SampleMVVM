package com.example.osama.background

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.CountDownTimer
import android.util.Log

@SuppressLint("LogNotTimber")
class CounterDownService : Service() {

    private val TAG = "BroadcastService"

    companion object {
        val COUNTDOWN_BR = "com.osama.example.countdown_br"
    }

    var bi = Intent(COUNTDOWN_BR)

    private lateinit var cdt: CountDownTimer

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Starting timer...")
        cdt = object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000)
                bi.putExtra("countdown", millisUntilFinished)
                sendBroadcast(bi)
            }

            override fun onFinish() {
                Log.i(TAG, "Timer finished")

            }
        }
        cdt.start()
    }

    override fun onDestroy() {
        cdt.cancel()
        Log.i(TAG, "Timer cancelled")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

}
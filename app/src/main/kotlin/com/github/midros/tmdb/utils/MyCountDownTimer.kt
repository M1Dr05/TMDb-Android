package com.github.midros.tmdb.utils

import android.os.CountDownTimer
import com.pawegio.kandroid.i

/**
 * Created by luis rafael on 16/02/19.
 */
class MyCountDownTimer(startTime: Long, interval: Long, private val func: () -> Unit) : CountDownTimer(startTime, interval) {
    override fun onFinish() = func()
    override fun onTick(timer: Long) { i("TMDb","timer $timer")  }
}
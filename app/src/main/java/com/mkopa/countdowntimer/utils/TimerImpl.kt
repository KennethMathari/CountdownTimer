package com.mkopa.countdowntimer.utils

import android.os.CountDownTimer
import javax.inject.Inject

class TimerImpl @Inject constructor(): Timer {

    private var countDownTimer: CountDownTimer? = null
    override fun start(
        remainingTime: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit
    ) {
        countDownTimer = object : CountDownTimer(remainingTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                onTick.invoke(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish.invoke()
            }

        }.start()
    }

    override fun cancel() {
        countDownTimer?.cancel()
    }

}

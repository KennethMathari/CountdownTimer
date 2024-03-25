package com.mkopa.countdowntimer.utils

interface Timer {
    fun start(remainingTime: Long, interval: Long, onTick: (Long) -> Unit, onFinish: () -> Unit)
    fun cancel()
}
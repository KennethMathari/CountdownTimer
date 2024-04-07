package com.mkopa.countdowntimer.ui.state

import com.mkopa.countdowntimer.utils.BackgroundColor

data class CountDownTimerState(
    val countDownTimer: String? = null,
    val color: BackgroundColor = BackgroundColor.TRANSPARENT
)

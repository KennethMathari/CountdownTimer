package com.mkopa.countdowntimer.ui.state

import com.mkopa.countdowntimer.ui.viewmodel.KenColor
import com.mkopa.countdowntimer.utils.RemainingTime


sealed interface CountDownTimerState {
    data object Initial : CountDownTimerState
    data class Time(
        val remainingTime: RemainingTime,
        val color: KenColor
    ): CountDownTimerState
}

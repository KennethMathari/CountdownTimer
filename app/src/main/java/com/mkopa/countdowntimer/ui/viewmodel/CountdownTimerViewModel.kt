package com.mkopa.countdowntimer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import com.mkopa.countdowntimer.ui.state.CountDownTimerState
import com.mkopa.countdowntimer.utils.BackgroundColor
import com.mkopa.countdowntimer.utils.CountryIsoCode
import com.mkopa.countdowntimer.utils.Timer
import com.mkopa.countdowntimer.utils.toFormattedTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CountdownTimerViewModel @Inject constructor(
    private val activeUsagePeriodDataSource: ActiveUsagePeriodDataSource,
    private val countryIsoCodeDataSource: CountryIsoCodeDataSource,
    private val timer: Timer
) : ViewModel() {

    private val _countDownTimerState = MutableStateFlow(CountDownTimerState())
    val countDownTimerState: StateFlow<CountDownTimerState> get() = _countDownTimerState.asStateFlow()

    init {
        startTimer()
    }

    fun startTimer() {
        viewModelScope.launch {

            val remainingTime = activeUsagePeriodDataSource.getRemainingTime().toMillis()
            val countryIsoCode = countryIsoCodeDataSource.getCountryIsoCode()

            val warningThreshold = when (countryIsoCode) {
                CountryIsoCode.UG -> TimeUnit.HOURS.toMillis(3)
                else -> TimeUnit.HOURS.toMillis(2)
            }

            timer.start(remainingTime, 1000, { millisUntilFinished ->
                val color = if (millisUntilFinished <= warningThreshold) BackgroundColor.ORANGE
                else BackgroundColor.GREEN

                _countDownTimerState.value = CountDownTimerState(
                    countDownTimer = millisUntilFinished.toFormattedTimeString(),
                    color = color
                )

            }, {
                _countDownTimerState.value = CountDownTimerState(
                    countDownTimer = "00:00:00",
                    color = BackgroundColor.RED
                )
            })
        }
    }

}
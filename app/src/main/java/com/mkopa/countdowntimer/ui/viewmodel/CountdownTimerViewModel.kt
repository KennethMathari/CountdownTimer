package com.mkopa.countdowntimer.ui.viewmodel

import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import com.mkopa.countdowntimer.ui.state.CountDownTimerState
import com.mkopa.countdowntimer.utils.CountryIsoCode
import com.mkopa.countdowntimer.utils.toFormattedTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CountdownTimerViewModel @Inject constructor(
    private val activeUsagePeriodDataSource: ActiveUsagePeriodDataSource,
    private val countryIsoCodeDataSource: CountryIsoCodeDataSource
) : ViewModel() {

    private val _countDownTimerState = MutableStateFlow(CountDownTimerState())
    val countDownTimerState: StateFlow<CountDownTimerState> get() = _countDownTimerState.asStateFlow()

    init {
        getRemainingTime()
    }

    private fun getRemainingTime() {
        viewModelScope.launch {
            val remainingTime = activeUsagePeriodDataSource.getRemainingTime().toMillis()
            val countryIsoCode = countryIsoCodeDataSource.getCountryIsoCode()

            val warningThreshold = when (countryIsoCode) {
                CountryIsoCode.UG -> TimeUnit.HOURS.toMillis(3)
                else -> TimeUnit.HOURS.toMillis(2)
            }

            object : CountDownTimer(remainingTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    _countDownTimerState.update { currentState ->
                        val color =
                            if (millisUntilFinished <= warningThreshold) Color(red = 255, green = 165, blue = 0) else Color.Green
                        currentState.copy(
                            countDownTimer = millisUntilFinished.toFormattedTimeString(),
                            color = color
                        )
                    }
                }

                override fun onFinish() {
                    _countDownTimerState.update { currentState ->
                        currentState.copy(
                            countDownTimer = "00:00:00", color = Color.Red
                        )
                    }
                }

            }.start()
        }
    }


}
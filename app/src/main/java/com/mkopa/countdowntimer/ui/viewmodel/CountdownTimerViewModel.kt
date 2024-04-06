package com.mkopa.countdowntimer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import com.mkopa.countdowntimer.ui.state.CountDownTimerState
import com.mkopa.countdowntimer.utils.CountryIsoCode
import com.mkopa.countdowntimer.utils.Timer
import com.mkopa.countdowntimer.utils.getRemaingTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.exp

@HiltViewModel
class CountdownTimerViewModel @Inject constructor(
    private val activeUsagePeriodDataSource: ActiveUsagePeriodDataSource,
    private val countryIsoCodeDataSource: CountryIsoCodeDataSource,
    private val timer: Timer
) : ViewModel() {

    private var job: Job? = null
    private val _countDownTimerState : MutableStateFlow<CountDownTimerState> = MutableStateFlow(CountDownTimerState.Initial)
    val countDownTimerState: StateFlow<CountDownTimerState> get() = _countDownTimerState.asStateFlow()

    private val expiryTime = 1712332840000


    fun getRemainingTime() {
        println("current time ${System.currentTimeMillis()}")
        println("expiry time ${expiryTime}")
        println("diff ${expiryTime - System.currentTimeMillis()}")
        job?.cancel()
        job = viewModelScope.launch {
            while (true) {
                val countryIsoCode = countryIsoCodeDataSource.getCountryIsoCode()

                val warningThreshold = when (countryIsoCode) {
                    CountryIsoCode.UG -> TimeUnit.HOURS.toMillis(3)
                    else -> TimeUnit.HOURS.toMillis(2)
                }
                val remainingTime = getRemaingTime(time = expiryTime)
                val isTimeOut = expiryTime <= warningThreshold
                val color = if (isTimeOut) KenColor.ORANGE else KenColor.GREEN
                _countDownTimerState.value = CountDownTimerState.Time(
                    remainingTime = remainingTime,
                    color = color
                )
                delay(1_000)

            }
        }


    }

    fun cancelTimer() {
        job?.cancel()
    }

}


enum class KenColor {
    GREEN,
    RED,
    ORANGE,
    Transparent,
}
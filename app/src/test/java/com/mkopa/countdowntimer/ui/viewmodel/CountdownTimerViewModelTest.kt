package com.mkopa.countdowntimer.ui.viewmodel

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import com.mkopa.countdowntimer.data.model.ActiveUsagePeriod
import com.mkopa.countdowntimer.utils.CountryIsoCode
import com.mkopa.countdowntimer.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class CountdownTimerViewModelTest {
    private lateinit var countdownTimerViewModel: CountdownTimerViewModel
    private val activeUsagePeriodDataSource = mockk<ActiveUsagePeriodDataSource>()
    private val countryIsoCodeDataSource = mockk<CountryIsoCodeDataSource>()
    private val lockTime: OffsetDateTime =
        OffsetDateTime.now().withHour(23).withMinute(0).withSecond(0)

    private val currentTime: OffsetDateTime = OffsetDateTime.now()
    private val expectedDuration: Duration = Duration.between(currentTime, lockTime)


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {

        coEvery {
            activeUsagePeriodDataSource.getLockingInfo()
        } returns ActiveUsagePeriod(
            lockTime = lockTime
        )

        coEvery {
            activeUsagePeriodDataSource.getRemainingTime()
        } returns expectedDuration

        coEvery {
            countryIsoCodeDataSource.getCountryIsoCode()
        } returns CountryIsoCode.UG

        countdownTimerViewModel =
            CountdownTimerViewModel(activeUsagePeriodDataSource, countryIsoCodeDataSource)
    }

    @Test
    fun `test countdown timer behavior`() = runTest {

        countdownTimerViewModel.countDownTimerState.test {
            assertEquals(
                null,
                awaitItem().color
            )
            countdownTimerViewModel.getRemainingTime()

            assertEquals(
                Color.Green,
                awaitItem().color
            )

            cancelAndIgnoreRemainingEvents()
        }
    }


}
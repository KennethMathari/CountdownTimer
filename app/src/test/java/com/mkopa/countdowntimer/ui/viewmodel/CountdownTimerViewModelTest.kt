package com.mkopa.countdowntimer.ui.viewmodel

import app.cash.turbine.test
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import com.mkopa.countdowntimer.data.model.ActiveUsagePeriod
import com.mkopa.countdowntimer.utils.BackgroundColor
import com.mkopa.countdowntimer.utils.CountryIsoCode
import com.mkopa.countdowntimer.utils.MainDispatcherRule
import com.mkopa.countdowntimer.utils.Timer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.OffsetDateTime

class CountdownTimerViewModelTest {
    private lateinit var countdownTimerViewModel: CountdownTimerViewModel
    private val activeUsagePeriodDataSource = mockk<ActiveUsagePeriodDataSource>()
    private val timer = mockk<Timer>()
    private val countryIsoCodeDataSource = mockk<CountryIsoCodeDataSource>()
    private val lockTime: OffsetDateTime =
        OffsetDateTime.now().withHour(23).withMinute(0).withSecond(0)

    private val currentTime: OffsetDateTime = OffsetDateTime.now()
    private val expectedDuration = Duration.between(currentTime, lockTime)


    private val remainingTime = expectedDuration.toMillis()
    private val onTickSlot = slot<(Long) -> Unit>()
    private val onFinishSlot = slot<() -> Unit>()


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
            CountdownTimerViewModel(activeUsagePeriodDataSource, countryIsoCodeDataSource, timer)


    }


    @Test
    fun `test startTimer() updates state correctly when timer starts`() = runTest {

        coEvery {
            timer.start(remainingTime, 1000, capture(onTickSlot), capture(onFinishSlot))
        } answers {
            val onTick: (Long) -> Unit = onTickSlot.captured
            onTick.invoke(remainingTime)
        }

        val countDownTimerState = countdownTimerViewModel.countDownTimerState

        countDownTimerState.test {
            assertEquals(
                BackgroundColor.TRANSPARENT, awaitItem().color
            )

            countdownTimerViewModel.startTimer()

            assertEquals(BackgroundColor.GREEN, awaitItem().color)

            //3hrs to end of countdown
            // assertEquals(BackgroundColor.ORANGE, awaitItem().color)

            expectNoEvents()
        }

    }


    @Test
    fun `test startTimer() updates state correctly when timer stops`() = runTest {
        coEvery {
            timer.start(remainingTime, 1000, capture(onTickSlot), capture(onFinishSlot))
        } answers {
            val onFinish: () -> Unit = onFinishSlot.captured
            onFinish.invoke()
        }

        val countDownTimerState = countdownTimerViewModel.countDownTimerState

        countDownTimerState.test {
            assertEquals(
                BackgroundColor.TRANSPARENT, awaitItem().color
            )

            countdownTimerViewModel.startTimer()

            assertEquals(BackgroundColor.RED, awaitItem().color)

            expectNoEvents()
        }
    }

}
package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.data.model.ActiveUsagePeriod
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Duration
import java.time.OffsetDateTime

class ActiveUsagePeriodDataSourceTest {
    private val activeUsagePeriodDataSource = mockk<ActiveUsagePeriodDataSource>()
    private val lockTime: OffsetDateTime =
        OffsetDateTime.now().withHour(23).withMinute(0).withSecond(0)


    @Before
    fun setUp() {
        coEvery {
            activeUsagePeriodDataSource.getLockingInfo()
        } returns ActiveUsagePeriod(
            lockTime = lockTime
        )
    }

    @Test
    fun `test getLockingInfo returns correct lockTime`() = runTest {

        val result = activeUsagePeriodDataSource.getLockingInfo()

        assertEquals(
            lockTime, result.lockTime
        )
    }

    @Test
    fun `test getRemainingTime returns correct duration`() = runTest {

        val currentTime = OffsetDateTime.now()
        val expectedDuration = Duration.between(currentTime, lockTime)

        coEvery {
            activeUsagePeriodDataSource.getRemainingTime()
        } returns expectedDuration

        val result = activeUsagePeriodDataSource.getRemainingTime()

        assertEquals(expectedDuration, result)
    }

}
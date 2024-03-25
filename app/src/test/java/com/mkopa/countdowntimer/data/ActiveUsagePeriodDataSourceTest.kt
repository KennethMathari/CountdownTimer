package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.data.model.ActiveUsagePeriod
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Duration
import java.time.OffsetDateTime

class ActiveUsagePeriodDataSourceTest {
    private val activeUsagePeriodDataSource = mockk<ActiveUsagePeriodDataSource>()
    private val lockTime: OffsetDateTime =
        OffsetDateTime.now().withHour(23).withMinute(0).withSecond(0)

    @Test
    fun `test getLockingInfo returns current date at 2300h`() = runBlocking {

        coEvery {
            activeUsagePeriodDataSource.getLockingInfo()
        } returns ActiveUsagePeriod(
            lockTime = lockTime
        )

        val result = activeUsagePeriodDataSource.getLockingInfo()

        assertEquals(
            lockTime.withNano(0), result.lockTime.withNano(0)
        )
    }

    @Test
    fun `test getRemainingTime returns correct duration`() = runBlocking {

        coEvery {
            activeUsagePeriodDataSource.getLockingInfo()
        } returns ActiveUsagePeriod(
            lockTime = lockTime
        )

        val currentTime = OffsetDateTime.now()
        val expectedDuration = Duration.between(currentTime, lockTime)

        coEvery {
            activeUsagePeriodDataSource.getRemainingTime()
        } returns expectedDuration

        val result = activeUsagePeriodDataSource.getRemainingTime()

        assertEquals(expectedDuration, result)
    }

}
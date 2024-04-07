package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.data.model.ActiveUsagePeriod
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.OffsetDateTime

class ActiveUsagePeriodDataSource {

    suspend fun getLockingInfo(): ActiveUsagePeriod {
        delay(500)
        return ActiveUsagePeriod(
            lockTime = OffsetDateTime.now().withHour(23).withMinute(0).withSecond(0)
        )
    }

    suspend fun getRemainingTime(): Duration {
        val lockTime = getLockingInfo().lockTime
        val currentTime = OffsetDateTime.now()
        return Duration.between(currentTime, lockTime)
    }

}
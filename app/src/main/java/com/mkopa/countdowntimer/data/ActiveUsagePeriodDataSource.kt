package com.mkopa.countdowntimer.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.mkopa.countdowntimer.data.model.ActiveUsagePeriod
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.OffsetDateTime

@RequiresApi(Build.VERSION_CODES.O)
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
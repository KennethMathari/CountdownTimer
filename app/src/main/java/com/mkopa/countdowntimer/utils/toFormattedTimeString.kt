package com.mkopa.countdowntimer.utils

import java.time.Instant
import java.util.concurrent.TimeUnit

fun Long.toFormattedTimeString(): String {
    val hours = this / (1000 * 60 * 60)
    val minutes = (this % (1000 * 60 * 60)) / (1000 * 60)
    val seconds = (this % (1000 * 60)) / 1000
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

data class RemainingTime(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
)

fun getRemaingTime(time: Long): RemainingTime {
    val current = System.currentTimeMillis()
    val diff = time - current

    val hours = TimeUnit.MILLISECONDS.toHours(diff)

    val minsDiff = diff - TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(minsDiff)

    val secsDiff = diff - (TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(minutes))
    val seconds = TimeUnit.MILLISECONDS.toSeconds(secsDiff)

    println("hours $hours : $minutes $seconds")
    return RemainingTime(
        hours = hours.toInt(),
        minutes = minutes.toInt(),
        seconds = seconds.toInt()
    )
}
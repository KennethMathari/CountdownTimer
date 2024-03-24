package com.mkopa.countdowntimer.utils

fun Long.toFormattedTimeString(): String {
    val hours = this / (1000 * 60 * 60)
    val minutes = (this % (1000 * 60 * 60)) / (1000 * 60)
    val seconds = (this % (1000 * 60)) / 1000
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

package com.mkopa.countdowntimer.utils

import androidx.compose.ui.graphics.Color
import com.mkopa.countdowntimer.utils.BackgroundColor.*

enum class BackgroundColor {
    GREEN,
    ORANGE,
    RED,
    TRANSPARENT
}


fun getBackgroundColor(backgroundColor: BackgroundColor): Color = when(backgroundColor){
    GREEN -> Color.Green
    ORANGE -> Color(red = 255, green = 165, blue = 0)
    RED -> Color.Red
    TRANSPARENT -> Color.Transparent
}
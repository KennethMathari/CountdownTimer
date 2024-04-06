package com.mkopa.countdowntimer.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mkopa.countdowntimer.ui.state.CountDownTimerState
import com.mkopa.countdowntimer.ui.viewmodel.CountdownTimerViewModel
import com.mkopa.countdowntimer.ui.viewmodel.KenColor

@Composable
fun CountDownTimer() {

    val countdownTimerViewModel = hiltViewModel<CountdownTimerViewModel>()
    val countdownTimerState by countdownTimerViewModel.countDownTimerState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        countdownTimerViewModel.getRemainingTime()
        onDispose {
            countdownTimerViewModel.cancelTimer()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (val state = countdownTimerState) {
            CountDownTimerState.Initial -> {}
            is CountDownTimerState.Time -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .height(150.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = getBackgroundColor(kenColor = state.color)
                    ),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        val hours = state.remainingTime.hours
                        val mins = state.remainingTime.minutes
                        val secs = state.remainingTime.seconds

                        val formatted = String.format("%2d:%2d:%2d", hours, mins, secs)
                        Text(
                            text = formatted,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )

                    }
                }
            }
        }


    }
}


fun getBackgroundColor(kenColor: KenColor): Color = when (kenColor) {
    KenColor.GREEN -> Color.Green
    KenColor.RED -> Color.Red
    KenColor.ORANGE -> Color(red = 255, green = 165, blue = 0)
    KenColor.Transparent -> Color.Transparent
}

@Preview
@Composable
fun CountDownTimerPreview() {
    CountDownTimer()
}
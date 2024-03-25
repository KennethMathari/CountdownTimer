package com.mkopa.countdowntimer.di

import com.mkopa.countdowntimer.utils.Timer
import com.mkopa.countdowntimer.utils.TimerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimerModule {
    @Binds
    @Singleton
    abstract fun bindTimer(timerImpl: TimerImpl): Timer
}
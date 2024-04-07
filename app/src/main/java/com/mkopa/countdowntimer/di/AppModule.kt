package com.mkopa.countdowntimer.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import com.mkopa.countdowntimer.utils.Timer
import com.mkopa.countdowntimer.utils.TimerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideActiveUsagePeriodDataSource(): ActiveUsagePeriodDataSource{
        return ActiveUsagePeriodDataSource()
    }

    @Provides
    fun provideCountryIsoCodeDataSource(): CountryIsoCodeDataSource{
        return CountryIsoCodeDataSource()
    }
}
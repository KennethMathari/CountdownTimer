package com.mkopa.countdowntimer.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.mkopa.countdowntimer.data.ActiveUsagePeriodDataSource
import com.mkopa.countdowntimer.data.CountryIsoCodeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun provideActiveUsagePeriodDataSource(): ActiveUsagePeriodDataSource{
        return ActiveUsagePeriodDataSource()
    }

    @Provides
    fun provideCountryIsoCodeDataSource(): CountryIsoCodeDataSource{
        return CountryIsoCodeDataSource()
    }
}
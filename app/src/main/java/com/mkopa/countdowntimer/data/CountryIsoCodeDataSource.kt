package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.utils.CountryIsoCode

class CountryIsoCodeDataSource {
    suspend fun getCountryIsoCode(): CountryIsoCode{
        return CountryIsoCode.UG
    }
}
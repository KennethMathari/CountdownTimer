package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.utils.CountryIsoCode
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryIsoCodeDataSourceTest {

    @Test
    fun `test getCountryIsoCode returns correct value`() = runBlocking {
        val countryIsoCodeDataSource = CountryIsoCodeDataSource()

        val result = countryIsoCodeDataSource.getCountryIsoCode()

        assertEquals(
            CountryIsoCode.UG,
            result
        )
    }
}
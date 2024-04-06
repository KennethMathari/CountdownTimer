package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.utils.CountryIsoCode
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryIsoCodeDataSourceTest {

    @Test
    fun `test getCountryIsoCode returns correct value`() {
        val countryIsoCodeDataSource = mockk<CountryIsoCodeDataSource>()

        every {
            countryIsoCodeDataSource.getCountryIsoCode()
        } returns CountryIsoCode.UG

        val result = countryIsoCodeDataSource.getCountryIsoCode()

        assertEquals(
            CountryIsoCode.UG, result
        )
    }
}
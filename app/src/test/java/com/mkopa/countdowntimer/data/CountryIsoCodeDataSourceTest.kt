package com.mkopa.countdowntimer.data

import com.mkopa.countdowntimer.utils.CountryIsoCode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryIsoCodeDataSourceTest {

    @Test
    fun `test getCountryIsoCode returns correct value`() = runTest {
        val countryIsoCodeDataSource = mockk<CountryIsoCodeDataSource>()

        coEvery {
            countryIsoCodeDataSource.getCountryIsoCode()
        } returns CountryIsoCode.UG

        val result = countryIsoCodeDataSource.getCountryIsoCode()

        assertEquals(
            CountryIsoCode.UG, result
        )
    }
}
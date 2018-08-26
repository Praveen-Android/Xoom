package com.praveen.xoom.utils

import com.praveen.xoom.TestUtils
import com.praveen.xoom.TestUtils.getXoomApiResponse
import com.praveen.xoom.data.CountryItem
import com.praveen.xoom.database.CountryDetails
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class ConversionUtilsTest {

    private lateinit var countryItemsList:List<CountryItem?>
    private lateinit var countryDetailsList:List<CountryDetails>

    @Before
    fun setUp() {
        countryItemsList = getXoomApiResponse("test_countries.json").items!!
        countryDetailsList = TestUtils.getListOfCountryDetails()
    }

    @After
    fun tearDown() {
        countryItemsList = listOf()
        countryDetailsList = listOf()
    }

    @Test
    fun testCountryDetailsCreation(){
        assertNotNull(countryItemsList)
        assertEquals(countryItemsList.count(), 5)

        run {
            val countryDetails = ConversionUtils.getCountryDetails(countryItemsList[0]!!)
            assertEquals(countryDetails.countryName, "Argentina")
            assertEquals(countryDetails.countryCode, "AR")
            assertEquals(countryDetails.hasActiveDisbursement, true)
            assertEquals(countryDetails.isFavorite, false)
        }

        run {
            val countryDetails = ConversionUtils.getCountryDetails(countryItemsList[4]!!)
            assertEquals(countryDetails.countryName, "Denmark")
            assertEquals(countryDetails.countryCode, "DK")
            assertEquals(countryDetails.hasActiveDisbursement, false)
            assertEquals(countryDetails.isFavorite, false)
        }
    }

    @Test
    // Tests the logic to form the final list of countries with user selected favorites at the top
    fun testPrepareListWithFavorites() {
        // Without any changes, the list remain as is
        run {
            val list = ConversionUtils.prepareListWithFavorites(countryDetailsList, setOf())
            assertEquals(list.count(), 5)
            assertEquals(list[0].countryName, "Argentina")
            assertEquals(list[1].countryName, "Costa Rica")
            assertEquals(list[2].countryName, "Germany")
            assertEquals(list[3].countryName, "Spain")
            assertEquals(list[4].countryName, "Denmark")
        }

        // Up on selecting DE and DK as favorites, they move to the top of the list
        run {
            val list = ConversionUtils.prepareListWithFavorites(countryDetailsList, setOf("DE", "DK"))
            assertEquals(list.count(), 5)
            assertEquals(list[0].countryName, "Germany")
            assertEquals(list[1].countryName, "Denmark")
            assertEquals(list[2].countryName, "Argentina")
            assertEquals(list[3].countryName, "Costa Rica")
            assertEquals(list[4].countryName, "Spain")
        }

        // Up on changing the favorite to ES, ES moves to the top of the list which DE and DK move to their original spot
        run {
            val list = ConversionUtils.prepareListWithFavorites(countryDetailsList, setOf("ES"))
            assertEquals(list.count(), 5)
            assertEquals(list[0].countryName, "Spain")
            assertEquals(list[1].countryName, "Argentina")
            assertEquals(list[2].countryName, "Costa Rica")
            assertEquals(list[3].countryName, "Germany")
            assertEquals(list[4].countryName, "Denmark")
        }
    }
}
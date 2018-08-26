package com.praveen.xoom

import com.google.gson.Gson
import com.praveen.xoom.data.XoomResponse
import com.praveen.xoom.database.CountryDetails
import com.praveen.xoom.utils.ConversionUtils

object TestUtils {

    fun convertStreamToString(stream: java.io.InputStream): String {
        val s = java.util.Scanner(stream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    fun getXoomApiResponse(file: String): XoomResponse{
        val inputStream = this.javaClass.classLoader.getResourceAsStream(file)
        val jsonSample = convertStreamToString(inputStream)
        return Gson().fromJson(jsonSample, XoomResponse::class.java)
    }

    fun getCountryDetails(): CountryDetails {
        return CountryDetails(countryName = "Germany",
                countryCode = "DE",
                disbursementOptions = "",
                hasActiveDisbursement = true,
                isFavorite = true)
    }

    fun getListOfCountryDetails(): List<CountryDetails> {

        val countryItemsList = getXoomApiResponse("test_countries.json").items

        countryItemsList?:return listOf()

        val countryDetailsList = mutableListOf<CountryDetails>()

        run {
            for(item in countryItemsList){
                countryDetailsList.add(ConversionUtils.getCountryDetails(item!!))
            }
        }

        return countryDetailsList
    }


}
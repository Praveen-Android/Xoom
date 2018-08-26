package com.praveen.xoom.utils

import com.google.gson.Gson
import com.praveen.xoom.data.CountryItem
import com.praveen.xoom.data.DisbursementOptionsItem
import com.praveen.xoom.database.CountryDetails
import java.util.*

object ConversionUtils {

    fun getCountryDetails(item: CountryItem): CountryDetails {
        val countryCode = item.code
        val countryName = item.name
        val disbursementOptions = item.disbursementOptions
        val hasActiveDisbursementOptionsItem = getIfHasActiveDisbursementOptionsItem(item.disbursementOptions)

        return CountryDetails(countryCode = countryCode,
                countryName = countryName,
                disbursementOptions = Gson().toJson(disbursementOptions),
                hasActiveDisbursement = hasActiveDisbursementOptionsItem,
                isFavorite = false)
    }

    /**
     * @param list list of disbursement options
     * @return 'true' if the list has atleast one active disbursement option else 'false'
     */
    private fun getIfHasActiveDisbursementOptionsItem(list: List<DisbursementOptionsItem?>?): Boolean {
        list?:return false

        val iterator = list.listIterator()
        iterator.forEach {
             if(it?.mode == "ACTIVE"){
                 return true
             }
        }

        return false
    }

    /**
     * @param allCountries list of all country details that will be displayed on the UI
     * @param favorites list/set of user favorite countries
     * @return modified list of all countries with user favorites moved to the top so that the users would see their favorites at the top
     * of the list
     */
    fun prepareListWithFavorites(allCountries: List<CountryDetails>, favorites: Set<String>): List<CountryDetails> {
        for(country in allCountries){
            country.isFavorite = country.countryCode in favorites
        }

        return allCountries.sortedWith(Comparator { o1, o2 ->  o2.isFavorite.compareTo(o1.isFavorite)})
    }
}
package com.praveen.xoom.api

import com.praveen.xoom.database.CountryDetails
import io.reactivex.Single

interface ApiHelper {

    fun fetchCountryCatalogFromApi(): Single<List<CountryDetails>>

}
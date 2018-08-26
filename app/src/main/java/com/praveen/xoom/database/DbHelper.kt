package com.praveen.xoom.database

import io.reactivex.Single

interface DbHelper {

    fun saveCountryCatalogToDb(list: List<CountryDetails>)

    fun fetchCountryCatalogFromDb(): Single<List<CountryDetails>>

    fun getDbRowCount(): Single<Int>

    fun clearDb()
}
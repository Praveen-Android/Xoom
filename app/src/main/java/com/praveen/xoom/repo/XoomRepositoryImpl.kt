package com.praveen.xoom.repo

import com.praveen.xoom.api.ApiHelper
import com.praveen.xoom.database.CountryDetails
import com.praveen.xoom.database.DbHelper
import com.praveen.xoom.prefs.PreferenceHelper
import com.praveen.xoom.utils.ConversionUtils
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class XoomRepositoryImpl @Inject constructor(private val mApiHelper: ApiHelper,
                                             private val mDbHelper: DbHelper,
                                             private val mPreferenceHelper: PreferenceHelper): XoomRepository {

    /**
     * This method does the following:
     * 1. Check if database has any data to return and if yes then fetch from database
     * 2. Else, fetch the data from the server (api call)
     * 3. The data from these calls comes pre ordered by favorites
     */
    override fun fetchCountryCatalog() : Single<List<CountryDetails>> {

        return when(getDbRowCount().blockingGet()>0){
            true -> fetchCountryCatalogFromDb()
            else -> fetchCountryCatalogFromApi()
        }
    }

    /**
     * 1. fetch country catalog from server and save to database after fetching the data
     * 2. Then return the final list ordered by the favorites
     */
    override fun fetchCountryCatalogFromApi(): Single<List<CountryDetails>> {
        val favorites = fetchFavorites()

        return when(favorites.isEmpty()) {
        /**
         * 1. Clear the database, fetch the Country Catalog, save it to database after success and return the list
         */
            true -> {
                 mApiHelper.fetchCountryCatalogFromApi()
                         .doOnSubscribe {clearDb()}
                         .doOnSuccess {saveCountryCatalogToDb(it)}
            }
        /**
         * 1. Clear the database, fetch the Country Catalog and save it to database after success
         * 2. Use the RxJava2 zip operator to get (a) the country data streams from the api call and (b) the list of favorites, then prepare the final
         * data stream that has the favorite countries at the top of the list.
         */
            false -> {
                Single.zip(mApiHelper.fetchCountryCatalogFromApi()
                        .doOnSubscribe {clearDb()}
                        .doOnSuccess {saveCountryCatalogToDb(it)},
                        Single.just(favorites),
                        BiFunction<List<CountryDetails>,Set<String>, List<CountryDetails>>
                        {allCountries,favCountries -> ConversionUtils.prepareListWithFavorites(allCountries, favCountries)})
            }
        }
    }

    override fun saveCountryCatalogToDb(list: List<CountryDetails>) {
        return mDbHelper.saveCountryCatalogToDb(list)
    }

    /**
     * 1. fetch country catalog from database and then return the final list ordered by the favorites
     */
    override fun fetchCountryCatalogFromDb(): Single<List<CountryDetails>> {
        val favorites = fetchFavorites()

        return when(favorites.isEmpty()) {
        /**
         * 1. fetch the Country Catalog from database and return the list
         */
            true -> mDbHelper.fetchCountryCatalogFromDb()

        /**
         * 1. fetch the Country Catalog from database
         * 2. Use the RxJava2 zip operator to get (a) the country data streams from the database and (b) the list of favorites, then prepare the final
         * data stream that has the favorite countries at the top of the list.
         */
            false -> {
                Single.zip(mDbHelper.fetchCountryCatalogFromDb(), Single.just(favorites),
                        BiFunction<List<CountryDetails>,Set<String>, List<CountryDetails>>
                        {allCountries,favCountries -> ConversionUtils.prepareListWithFavorites(allCountries, favCountries)})
            }
        }
    }

    override fun getDbRowCount(): Single<Int> {
        return mDbHelper.getDbRowCount()
    }

    override fun clearDb() {
        mDbHelper.clearDb()
    }

    override fun saveFavorite(countryCode: String) {
        mPreferenceHelper.saveFavorite(countryCode)
    }

    override fun fetchFavorites(): Set<String> {
        return mPreferenceHelper.fetchFavorites()
    }

    override fun deleteFavorite(countryCode: String) {
        mPreferenceHelper.deleteFavorite(countryCode)
    }
}
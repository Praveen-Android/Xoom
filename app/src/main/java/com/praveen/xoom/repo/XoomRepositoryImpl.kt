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
     * 1. Check if db has any data to return and if yes then fetch the data from DB
     * 2. Else fetch the data from the server (api)
     * 3. Once the data is received, check if the user has any favorites and if yes, prepare the list to have the favorites at the top of the list
     * followed by other countries
     *
     */
    override fun fetchCountryCatalog() : Single<List<CountryDetails>> {

        val listOfCountries:Single<List<CountryDetails>> = when(getDbRowCount().blockingGet()>0){
            true -> fetchCountryCatalogFromDb()
            else -> fetchCountryCatalogFromApi()
        }

        val favorites = fetchFavorites()
        return when(favorites.isEmpty()) {

            true -> listOfCountries
            false -> {
                Single.zip(listOfCountries, Single.just(favorites),
                        BiFunction<List<CountryDetails>,Set<String>, List<CountryDetails>>
                        {allCountries,favCountries -> ConversionUtils.prepareListWithFavorites(allCountries, favCountries)})
            }
        }
    }

    /**
     * fetch country catalog from server and save to DB after fetching the data
     */
    override fun fetchCountryCatalogFromApi(): Single<List<CountryDetails>> {
        val favorites = fetchFavorites()

        return when(favorites.isEmpty()) {
            true -> {
                 mApiHelper.fetchCountryCatalogFromApi()
                         .doOnSubscribe {clearDb()}
                         .doOnSuccess {saveCountryCatalogToDb(it)}
            }
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

    override fun fetchCountryCatalogFromDb(): Single<List<CountryDetails>> {
        return mDbHelper.fetchCountryCatalogFromDb()
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
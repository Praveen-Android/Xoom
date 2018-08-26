package com.praveen.xoom.api

import com.praveen.xoom.database.CountryDetails
import com.praveen.xoom.utils.ConversionUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val mXoomService: XoomService): ApiHelper {

    /**
     * This method does the following
     *
     * 1. Makes an api call to xoom mobile end point
     * 2. Transforms the received response to a list of objects (CountryDetails) and is the entity that is inserted in to DB or shown on the UI
     * 3. Filters out the response to receive only a list of Countries who has active Disbursements
     * 4. Finally, this method makes the api call and returns a list of CountryDetails objects who have active disbursements
     */
    override fun fetchCountryCatalogFromApi(): Single<List<CountryDetails>> {
        return mXoomService.requestXoomCountryCatalog(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map{xoomResponse -> xoomResponse.items}
                .flatMap { list -> Observable.fromIterable(list) }
                .map { countryItem -> ConversionUtils.getCountryDetails(countryItem) }
                .filter { details: CountryDetails -> details.hasActiveDisbursement }
                .toList()
    }
}
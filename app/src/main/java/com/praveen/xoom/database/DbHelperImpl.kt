package com.praveen.xoom.database

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class DbHelperImpl @Inject constructor(private val xoomDataBase: XoomDataBase): DbHelper {

    override fun saveCountryCatalogToDb(list: List<CountryDetails>) {
        Completable.fromAction { xoomDataBase.mXoomDao().insertAll(list) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun fetchCountryCatalogFromDb(): Single<List<CountryDetails>> {
        return xoomDataBase.mXoomDao().getAllCountries()
                .subscribeOn(Schedulers.io())

    }

    override fun getDbRowCount(): Single<Int> {
        return xoomDataBase.mXoomDao().getCountryCount()
                .subscribeOn(Schedulers.io())
    }

    override fun clearDb() {
        Completable.fromAction {xoomDataBase.mXoomDao().deleteDbData()}
                .subscribeOn(Schedulers.io())
                .subscribe()

    }
}
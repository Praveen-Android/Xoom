package com.praveen.xoom.repo

import com.praveen.xoom.api.ApiHelper
import com.praveen.xoom.database.CountryDetails
import com.praveen.xoom.database.DbHelper
import com.praveen.xoom.prefs.PreferenceHelper
import io.reactivex.Single

interface XoomRepository : ApiHelper, DbHelper, PreferenceHelper {

    fun fetchCountryCatalog(): Single<List<CountryDetails>>

}
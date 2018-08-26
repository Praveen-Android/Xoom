package com.praveen.xoom.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface XoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countryCatalog: List<CountryDetails>)

    @Query("SELECT * FROM xoom_countries")
    fun getAllCountries(): Single<List<CountryDetails>>

    @Query("SELECT COUNT(*) FROM xoom_countries")
    fun getCountryCount(): Single<Int>

    @Query("DELETE FROM xoom_countries")
    fun deleteDbData()
}
package com.praveen.xoom.database

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.praveen.xoom.TestUtils
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class XoomDaoTest {

    private lateinit var mDatabase: XoomDataBase

    @Before
    @Throws(Exception::class)
    fun initDb(){
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), XoomDataBase::class.java)
                        // allow queries to run on main thread only for testing purposes
                        .allowMainThreadQueries()
                        .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDb(){
        mDatabase.close();
    }

    @Test
    fun testWhenNoCountriesInserted(){
        mDatabase.mXoomDao().getCountryCount().test().assertValue(0)
    }

    @Test
    fun testInsertCountries(){
        val countriesList = TestUtils.getListOfCountryDetails()
        mDatabase.mXoomDao().insertAll(countriesList)

        //verify inserted countries count
        mDatabase.mXoomDao().getCountryCount().test().assertValue(5)

        //verify source and target insertion countries count is the same
        mDatabase.mXoomDao().getAllCountries().test().assertValue({ list -> list.count()==countriesList.count() })

        // verify the data from database is correct
        mDatabase.mXoomDao().getAllCountries().test().assertValue({ list ->
            list[0].countryCode==countriesList[0].countryCode &&
            list[0].countryName==countriesList[0].countryName &&
            list[1].countryCode==countriesList[1].countryCode &&
            list[1].countryName==countriesList[1].countryName })
    }

    @Test
    fun testDeleteDb(){
        val countriesList = TestUtils.getListOfCountryDetails()
        mDatabase.mXoomDao().insertAll(countriesList)
        mDatabase.mXoomDao().getCountryCount().test().assertValue(5)
        mDatabase.mXoomDao().deleteDbData()
        mDatabase.mXoomDao().getCountryCount().test().assertValue(0)
    }
}
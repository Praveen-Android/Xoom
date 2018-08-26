package com.praveen.xoom.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(CountryDetails::class)], version = 1, exportSchema = false)
abstract class XoomDataBase: RoomDatabase() {

    abstract fun mXoomDao():XoomDao

}
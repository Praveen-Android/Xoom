package com.praveen.xoom.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = DbDescriptor.TABLE_COUNTRY_LIST)
data class CountryDetails (

        @PrimaryKey(autoGenerate = true)
        var id:Long? = null,

        @ColumnInfo(name = "countryName")
        var countryName:String? = null,

        @ColumnInfo(name = "countryCode")
        var countryCode:String? = null,

        @ColumnInfo(name = "disbursementOptions")
        var disbursementOptions:String? = null,

        @ColumnInfo(name = "hasActiveDisbursementOption")
        var hasActiveDisbursement:Boolean = false,

        @Ignore
        var isFavorite:Boolean = false
)
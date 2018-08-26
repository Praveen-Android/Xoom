package com.praveen.xoom.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = DbDescriptor.TABLE_COUNTRY_LIST)
data class CountryDetails(

        @PrimaryKey(autoGenerate = true)
        var id:Long? = null,

        @ColumnInfo(name = "countryName")
        var countryName:String?,

        @ColumnInfo(name = "countryCode")
        var countryCode:String?,

        @ColumnInfo(name = "disbursementOptions")
        var disbursementOptions:String?,

        @ColumnInfo(name = "hasActiveDisbursementOption")
        var hasActiveDisbursement:Boolean,

        @ColumnInfo(name = "isFavorite")
        var isFavorite:Boolean
)
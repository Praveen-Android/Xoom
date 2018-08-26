package com.praveen.xoom.di

import android.arch.persistence.room.Room
import android.content.Context
import com.praveen.xoom.database.DbDescriptor
import com.praveen.xoom.database.DbHelper
import com.praveen.xoom.database.DbHelperImpl
import com.praveen.xoom.database.XoomDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDbHelper(dbHelperImpl: DbHelperImpl): DbHelper {
        return dbHelperImpl
    }

    @Provides
    @Singleton
    fun provideXoomDatabase(@Named("DATABASE_NAME") dbName: String, context: Context): XoomDataBase {
        return Room.databaseBuilder(context, XoomDataBase::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Named("DATABASE_NAME")
    fun provideDatabaseName(): String {
        return DbDescriptor.XOOM_DATABASE
    }
}
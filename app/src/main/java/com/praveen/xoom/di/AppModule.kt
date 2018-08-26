package com.praveen.xoom.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.praveen.xoom.XoomApplication
import com.praveen.xoom.api.ApiHelper
import com.praveen.xoom.api.ApiHelperImpl
import com.praveen.xoom.prefs.PreferenceHelper
import com.praveen.xoom.prefs.PreferenceHelperImpl
import com.praveen.xoom.repo.XoomRepository
import com.praveen.xoom.repo.XoomRepositoryImpl
import com.praveen.xoom.ui.XoomViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val xoomApplication: XoomApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = xoomApplication

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper {
        return apiHelperImpl
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(preferenceHelperImpl: PreferenceHelperImpl): PreferenceHelper {
        return preferenceHelperImpl
    }

    @Provides
    @Singleton
    fun provideXoomRepository(xoomRepositoryImpl: XoomRepositoryImpl): XoomRepository {
        return xoomRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(xoomRepository: XoomRepository): XoomViewModelFactory {
        return XoomViewModelFactory(xoomRepository)
    }

}
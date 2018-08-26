package com.praveen.xoom.di

import com.google.gson.Gson
import com.praveen.xoom.api.ApiEndPoint
import com.praveen.xoom.api.XoomService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideXoomService(retrofit: Retrofit): XoomService = retrofit.create(XoomService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(ApiEndPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideOKHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

}
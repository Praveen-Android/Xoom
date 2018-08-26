package com.praveen.xoom

import android.app.Application
import com.praveen.xoom.di.*

class XoomApplication: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        setUpDagger()
    }

    private fun setUpDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule())
                .dbModule(DbModule()).build()
    }
}
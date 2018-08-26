package com.praveen.xoom.di

import com.praveen.xoom.ui.XoomAdapter
import com.praveen.xoom.ui.XoomFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [(AppModule::class), (ApiModule::class), (DbModule::class)])
interface AppComponent {

    fun inject(xoomFragment: XoomFragment)

    fun inject(xoomAdapter: XoomAdapter)
}
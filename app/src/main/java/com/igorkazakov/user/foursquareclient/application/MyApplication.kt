package com.igorkazakov.user.foursquareclient.application

import android.app.Application
import com.igorkazakov.user.foursquareclient.di.component.ApplicationComponent
import com.igorkazakov.user.foursquareclient.di.component.DaggerApplicationComponent
import com.igorkazakov.user.foursquareclient.di.module.ApplicationModule

class MyApplication : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
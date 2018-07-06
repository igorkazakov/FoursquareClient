package com.igorkazakov.user.foursquareclient.di.module

import android.content.Context
import android.location.LocationManager
import com.igorkazakov.user.foursquareclient.application.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: MyApplication) {

    @Singleton
    @Provides
    fun provideContext() : Context {
        return mApplication
    }

    @Singleton
    @Provides
    fun provideLocationService(context: Context) =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}
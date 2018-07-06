package com.igorkazakov.user.foursquareclient.di.module

import com.igorkazakov.user.foursquareclient.data.server.DataService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataServiceModule {

    @Singleton
    @Provides
    fun provideDataService() = DataService.instance
}
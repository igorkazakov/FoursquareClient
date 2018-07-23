package com.igorkazakov.user.foursquareclient.di.module

import com.igorkazakov.user.foursquareclient.data.RepositoryInterface
import com.igorkazakov.user.foursquareclient.data.server.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataServiceModule {

    @Singleton
    @Provides
    fun provideDataService() : RepositoryInterface {
        return Repository.instance
    }
}
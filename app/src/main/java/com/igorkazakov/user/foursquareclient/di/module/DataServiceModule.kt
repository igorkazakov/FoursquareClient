package com.igorkazakov.user.foursquareclient.di.module

import com.igorkazakov.user.foursquareclient.data.server.Repository
import com.igorkazakov.user.foursquareclient.data.server.RepositoryInterface
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
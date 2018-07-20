package com.igorkazakov.user.foursquareclient.di.module

import com.igorkazakov.user.foursquareclient.interactors.LocationInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideLocationInteractor() = LocationInteractor()
}
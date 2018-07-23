package com.igorkazakov.user.foursquareclient.di.module

import com.igorkazakov.user.foursquareclient.screens.iteractor.ShowVenuesOnMapInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideShowVenuesOnMapInteractor() = ShowVenuesOnMapInteractor()
}
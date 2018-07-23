package com.igorkazakov.user.foursquareclient.di.component


import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.di.module.ApplicationModule
import com.igorkazakov.user.foursquareclient.di.module.DataServiceModule
import com.igorkazakov.user.foursquareclient.di.module.InteractorModule
import com.igorkazakov.user.foursquareclient.screens.list.ListFragment
import com.igorkazakov.user.foursquareclient.screens.map.MapFragment
import com.igorkazakov.user.foursquareclient.screens.viewModel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DataServiceModule::class),
    (ApplicationModule::class),
    (InteractorModule::class)])
interface ApplicationComponent {

    fun inject(application: MyApplication)
    fun inject(listFragment: ListFragment)
    fun inject(mapFragment: MapFragment)
    fun inject(viewModelFactory: ViewModelFactory)
}
package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.location.LocationManager
import android.support.annotation.NonNull
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import javax.inject.Inject


class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var mApplication: MyApplication

    @Inject
    lateinit var mService: DataService

    @Inject
    lateinit var mLocationManager: LocationManager

    init {
        MyApplication.appComponent.inject(this)
    }

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return if (modelClass == ListFragmentViewModel::class.java) {
            ListFragmentViewModel(mApplication, mLocationManager) as T
        } else {
            ViewModelFactory() as T
        }
    }
}
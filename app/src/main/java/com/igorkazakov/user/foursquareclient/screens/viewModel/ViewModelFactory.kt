package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.location.LocationManager
import android.support.annotation.NonNull
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.RepositoryInterface
import com.igorkazakov.user.foursquareclient.screens.iteractor.ShowVenuesOnMapInteractor
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var mApplication: MyApplication

    @Inject
    lateinit var mRepository: RepositoryInterface

    @Inject
    lateinit var mLocationManager: LocationManager

    @Inject
    lateinit var mShowVenuesOnMapInteractor: ShowVenuesOnMapInteractor

    init {
        MyApplication.appComponent.inject(this)
    }

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return when (modelClass) {

            ListFragmentViewModel::class.java -> ListFragmentViewModel(
                    mApplication,
                    mRepository,
                    mShowVenuesOnMapInteractor) as T

            LocationViewModel::class.java -> LocationViewModel(
                    mApplication,
                    mLocationManager) as T

            MapFragmentVewModel::class.java -> MapFragmentVewModel(
                    mApplication,
                    mShowVenuesOnMapInteractor) as T

            else -> {
                ViewModelFactory() as T
            }
        }
    }
}
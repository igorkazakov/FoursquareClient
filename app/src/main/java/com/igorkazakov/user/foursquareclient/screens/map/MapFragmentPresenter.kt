package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import android.location.LocationManager
import com.arellomobile.mvp.InjectViewState
import com.igorkazakov.user.foursquareclient.screens.base.BaseMapPresenter

@InjectViewState
class MapFragmentPresenter(locationManager: LocationManager) : BaseMapPresenter<MapFragmentInterface>(locationManager) {

    private var myLocation: Location? = null
    private var mapIsReady: Boolean = false

    override fun locationChanged(location: Location) {
        myLocation = location
        if (mapIsReady) {
            viewState.showMyLocation(location)
        }
    }

    fun setMapReady(ready: Boolean) {
        mapIsReady = ready
        myLocation?.let {
            locationChanged(it)
        }
    }
}
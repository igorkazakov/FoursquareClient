package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import android.location.LocationManager
import com.arellomobile.mvp.InjectViewState
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.screens.base.map.BaseMapPresenter
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class MapFragmentPresenter(private val mService: DataService,
                           locationManager: LocationManager) :
        BaseMapPresenter<MapFragmentInterface>(locationManager) {

    private var myLocation: Location? = null
    private var mapIsReady: Boolean = false

    override fun locationChanged(location: Location) {
        myLocation = location
        if (mapIsReady) {
            viewState.showMyLocation(location)
            loadData(locationToString(location))
        }
    }

    fun setMapReady(ready: Boolean) {
        mapIsReady = ready
        myLocation?.let {
            locationChanged(it)
        }
    }

    private fun loadData(latLng: String) {

        mService.loadVenues(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe{ viewState.showLoading() }
                //.doOnTerminate { viewState.hideLoading() }
                .subscribe({
                    viewState.showVenuesOnMap(it)

                }, {
                    it.printStackTrace()
                })
    }
}
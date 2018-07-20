package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import android.location.LocationManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.interactors.LocationInteractor
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class MapFragmentPresenter(private val mService: DataService,
                           locationManager: LocationManager,
                           private var locationInteractor: LocationInteractor) :
        MvpPresenter<MapFragmentInterface>() {

    private var myLocation: Location? = null
    private var mapIsReady: Boolean = false

    init {
        locationInteractor.getLocationUpdates().subscribe {
            locationChanged(it)
        }
    }

    fun locationToString(location: Location): String {
        return "${location.latitude}, ${location.longitude}"
    }

    fun isLocationChanged(location: Location) : Boolean {

        if (myLocation == null) {
            myLocation = location
            return true
        }

        return location.distanceTo(location) >= 100f
    }

    fun locationChanged(location: Location) {
        myLocation = location
        if (/*isLocationChanged(location) && */mapIsReady) {
            viewState.showMyLocation(location)
            loadData(locationToString(location))
        }
    }
//not work
    fun setMapReady(ready: Boolean) {
        if (!mapIsReady) {
            mapIsReady = ready
            myLocation?.let {
                locationChanged(it)
            }
        }
    }

    private fun loadData(latLng: String) {

        mService.loadVenues(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ viewState.showLoading() }
                .doOnTerminate { viewState.hideLoading() }
                .subscribe({
                    viewState.showVenuesOnMap(it)

                }, {
                    it.printStackTrace()
                })
    }
}
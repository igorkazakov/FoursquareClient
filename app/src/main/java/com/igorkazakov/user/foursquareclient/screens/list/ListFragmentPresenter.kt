package com.igorkazakov.user.foursquareclient.screens.list

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils.Companion.ACCESS_COARSE_LOCATION
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils.Companion.ACCESS_FINE_LOCATION
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils.Companion.REQUEST_CODE_ACCESS_COARSE_LOCATION
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils.Companion.REQUEST_CODE_ACCESS_FINE_LOCATION
import io.reactivex.android.schedulers.AndroidSchedulers


@InjectViewState
class ListFragmentPresenter(private val mService: DataService,
                            private val mLocationManager: LocationManager) : MvpPresenter<ListFragmentInterface>() {

    private val mLocationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.e("LOCATION", location.toString())
            loadData(locationToString(location))
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        loadData("47.217693, 38.925319")
//    }

    fun locationToString(location: Location) : String {
        return "${location.latitude}, ${location.longitude}"
    }

    fun loadData(latLng: String) {

        mService.loadVenueRecommendations(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showVenues(it)

                }, {
                    it.printStackTrace()
                })
    }

    fun startLocationUpdates(fragment: ListFragment) {

        if (PermissionUtils.instance.checkPermission(fragment,
                        ACCESS_COARSE_LOCATION,
                        REQUEST_CODE_ACCESS_COARSE_LOCATION) &&
                PermissionUtils.instance.checkPermission(fragment,
                        ACCESS_FINE_LOCATION,
                        REQUEST_CODE_ACCESS_FINE_LOCATION)) {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 9999,
                    100f, mLocationListener)
        }
    }
}
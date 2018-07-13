package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils

abstract class BaseMapViewModel(application: MyApplication,
                                private val mLocationManager: LocationManager) : AndroidViewModel(application) {

    private var mLocationUpdatesInProgress: Boolean = false
    private val mLocationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            locationChanged(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    abstract fun locationChanged(location: Location)

    fun locationToString(location: Location): String {
        return "${location.latitude}, ${location.longitude}"
    }

    fun startLocationUpdates(fragment: Fragment) {

        if (!mLocationUpdatesInProgress) {

            mLocationUpdatesInProgress = true

            if (PermissionUtils.checkPermission(fragment,
                            PermissionUtils.ACCESS_COARSE_LOCATION,
                            PermissionUtils.REQUEST_CODE_ACCESS_COARSE_LOCATION) &&
                    PermissionUtils.checkPermission(fragment,
                            PermissionUtils.ACCESS_FINE_LOCATION,
                            PermissionUtils.REQUEST_CODE_ACCESS_FINE_LOCATION)) {

                val minTime = 9999L
                val minDistance = 100f

                when {

                    mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                minTime,
                                minDistance,
                                mLocationListener)

                    mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                minTime,
                                minDistance,
                                mLocationListener)

                    else -> {}//viewState.showLocationError()
                }

                val criteria = Criteria()
                val bestProvider = mLocationManager.getBestProvider(criteria, false)
                bestProvider?.let {
                    val location = mLocationManager.getLastKnownLocation(bestProvider)

                    location?.let {
                        mLocationListener.onLocationChanged(location)
                    }
                }
            }
        }
    }

    private fun stopLocationUpdates() {
        mLocationUpdatesInProgress = false
        mLocationManager.removeUpdates(mLocationListener)
    }

    override fun onCleared() {
        stopLocationUpdates()
        super.onCleared()
    }
}
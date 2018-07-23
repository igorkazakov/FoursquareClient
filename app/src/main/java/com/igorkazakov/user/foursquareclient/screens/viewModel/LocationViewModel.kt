package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils

class LocationViewModel(application: MyApplication,
                        private val mLocationManager: LocationManager) : AndroidViewModel(application) {

    val locationErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var locationLiveData: MutableLiveData<Location> = MutableLiveData()

    private val MIN_TIME = 0L
    private val MIN_DISTANCE = 100f
    private var mLocation: Location? = null
    private var mLocationUpdatesInProgress: Boolean = false

    private val mLocationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {

            locationLiveData.value = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun isLocationChanged(location: Location): Boolean {

        var result = true

        mLocation?.let {
            result = location.distanceTo(it) >= MIN_DISTANCE
        }

        mLocation = location
        return result
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

                when {

                    mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME,
                                MIN_DISTANCE,
                                mLocationListener)

                    mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                MIN_TIME,
                                MIN_DISTANCE,
                                mLocationListener)

                    else -> {
                        locationErrorLiveData.value = true
                    }
                }

                val criteria = Criteria()
                val bestProvider = mLocationManager.getBestProvider(criteria, false)
                bestProvider?.let {
                    val location = mLocationManager.getLastKnownLocation(bestProvider)

                    if (location != null) {

                        mLocationListener.onLocationChanged(location)
                    } else {
                        mLocationManager.requestSingleUpdate(criteria, mLocationListener, null)
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
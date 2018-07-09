package com.igorkazakov.user.foursquareclient.screens.base

import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils

abstract class BaseMapPresenter<T : BaseMapInterface>(private val mLocationManager: LocationManager) : MvpPresenter<T>() {

    private val mLocationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            locationChanged(location)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun attachView(view: T) {
        super.attachView(view)
        viewState.initUpdateLocation()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()


    }

    abstract fun locationChanged(location: Location)

    override fun detachView(view: T?) {
        super.detachView(view)
        stopLocationUpdates()
    }

    fun startLocationUpdates(fragment: Fragment) {

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

                else -> viewState.showLocationError()
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

    private fun stopLocationUpdates() {
        mLocationManager.removeUpdates(mLocationListener)
    }
}
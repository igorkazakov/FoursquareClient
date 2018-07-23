package com.igorkazakov.user.foursquareclient.screens.list

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.data.server.RepositoryInterface
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.interactors.ShowVenuesOnMapInteractor
import com.igorkazakov.user.foursquareclient.utils.PermissionUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import android.arch.lifecycle.OnLifecycleEvent




@InjectViewState
class ListFragmentPresenter(private val mRepository: RepositoryInterface,
                            private val mLocationManager: LocationManager,
                            private var showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) :
        MvpPresenter<ListFragmentInterface>(), LifecycleObserver {

    private val MIN_DISTANCE = 100f
    private val MIN_TIME = 0L
    private var myLocation: Location? = null
    private var disposable: Disposable? = null

    private val mLocationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {

            if (isLocationChanged(location)) {
                loadData(location)
            }

            myLocation = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initUpdateLocation()
    }

    fun isLocationChanged(location: Location) : Boolean {

        if (myLocation != null) {
            return location.distanceTo(myLocation) >= MIN_DISTANCE

        } else {
            return true
        }
    }

    fun startLocationUpdates(fragment: Fragment) {

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

                else -> viewState.showLocationError()
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

    private fun stopLocationUpdates() {
        mLocationManager.removeUpdates(mLocationListener)
    }

    override fun onDestroy() {
        stopLocationUpdates()
        super.onDestroy()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun viewDestroyed() {
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    private fun loadData(location: Location) {

        mRepository.loadVenueRecommendations(location)
                .doOnSubscribe {
                    disposable = it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ viewState.showLoading() }
                .doOnTerminate { viewState.hideLoading() }
                .subscribe({

                    val models = mutableListOf<VenueViewModel>()
                    it.forEach {
                        models.add(VenueViewModel(it))
                    }

                    viewState.showVenues(models)
                    showVenuesOnMapInteractor.postLocation(VenueMapModel(location, it))

                }, {
                    it.printStackTrace()
                })
    }

    fun showVenueDetail(model: VenueViewModel) {
        viewState.showVenueActivity(model)
    }
}
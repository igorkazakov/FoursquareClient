package com.igorkazakov.user.foursquareclient.screens.list

import android.location.Location
import android.location.LocationManager
import com.arellomobile.mvp.InjectViewState
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.screens.base.BaseMapPresenter
import io.reactivex.android.schedulers.AndroidSchedulers


@InjectViewState
class ListFragmentPresenter(private val mService: DataService,
                            locationManager: LocationManager) :
        BaseMapPresenter<ListFragmentInterface>(locationManager) {

    override fun locationChanged(location: Location) {
        loadData(locationToString(location))
    }

    fun locationToString(location: Location): String {
        return "${location.latitude}, ${location.longitude}"
    }

    private fun loadData(latLng: String) {

        mService.loadVenueRecommendations(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showVenues(it)

                }, {
                    it.printStackTrace()
                })
    }
}
package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import android.location.LocationManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.interactors.ShowVenuesOnMapInteractor
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class MapFragmentPresenter(private var showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) :
        MvpPresenter<MapFragmentInterface>() {


    private var mapIsReady: Boolean = false
    private var mVenueMapModel: VenueMapModel? = null

    init {
        showVenuesOnMapInteractor.getLocationUpdates().subscribe {
            locationChanged(it)
        }
    }

    private fun locationChanged(model: VenueMapModel) {

        mVenueMapModel = model
        if (mapIsReady) {

            viewState.showVenuesOnMap(model)
        }
    }

    fun setMapReady(ready: Boolean) {
        mapIsReady = ready

        if (mapIsReady) {
            mVenueMapModel?.let {
                locationChanged(it)
            }
        }
    }
}
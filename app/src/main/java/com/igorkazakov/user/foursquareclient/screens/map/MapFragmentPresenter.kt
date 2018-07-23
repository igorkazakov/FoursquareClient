package com.igorkazakov.user.foursquareclient.screens.map

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.interactors.ShowVenuesOnMapInteractor

@InjectViewState
class MapFragmentPresenter(showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) :
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
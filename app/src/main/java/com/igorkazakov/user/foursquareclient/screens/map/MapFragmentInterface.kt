package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.screens.progress.LoadingInterface

interface MapFragmentInterface : MvpView, LoadingInterface {

    //@StateStrategyType(value = SkipStrategy::class)
    //fun showMyLocation(latLng: Location)

    @StateStrategyType(value = SkipStrategy::class)
    fun showVenuesOnMap(model: VenueMapModel)
}
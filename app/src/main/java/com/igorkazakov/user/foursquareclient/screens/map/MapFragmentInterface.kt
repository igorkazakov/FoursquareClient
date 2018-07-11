package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import com.igorkazakov.user.foursquareclient.screens.base.map.BaseMapInterface
import com.igorkazakov.user.foursquareclient.screens.progress.LoadingInterface

interface MapFragmentInterface : BaseMapInterface, LoadingInterface {

    @StateStrategyType(value = SingleStateStrategy::class)
    fun showMyLocation(latLng: Location)

    @StateStrategyType(value = SkipStrategy::class)
    fun showVenuesOnMap(venues: List<Venue>)
}
package com.igorkazakov.user.foursquareclient.screens.list

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.screens.base.BaseMapInterface

interface ListFragmentInterface : BaseMapInterface {

    @StateStrategyType(value = SingleStateStrategy::class)
    fun showVenues(venues: List<VenueViewModel>)
}
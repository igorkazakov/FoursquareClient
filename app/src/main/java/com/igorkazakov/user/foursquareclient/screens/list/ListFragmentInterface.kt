package com.igorkazakov.user.foursquareclient.screens.list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.screens.progress.LoadingInterface

interface ListFragmentInterface : MvpView, LoadingInterface {

    @StateStrategyType(value = SkipStrategy::class)
    fun showLocationError()

    @StateStrategyType(value = SkipStrategy::class)
    fun initUpdateLocation()

    @StateStrategyType(value = SkipStrategy::class)
    //@StateStrategyType(value = SingleStateStrategy::class)
    fun showVenues(venues: List<VenueViewModel>)

    @StateStrategyType(value = SkipStrategy::class)
    fun showVenueActivity(model: VenueViewModel)
}
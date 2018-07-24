package com.igorkazakov.user.foursquareclient.screens.list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.screens.progress.LoadingInterface

@StateStrategyType(value = SkipStrategy::class)
interface ListFragmentInterface : MvpView, LoadingInterface {

    fun showLocationError()
    fun showVenueActivity(model: VenueViewModel)
    fun initUpdateLocation()

    @StateStrategyType(value = SingleStateStrategy::class)
    fun showVenues(venues: List<VenueViewModel>)



}
package com.igorkazakov.user.foursquareclient.screens.venue

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

interface VenueInterface : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun initView(model: VenueViewModel)
}
package com.igorkazakov.user.foursquareclient.screens.base.map

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseMapInterface : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun showLocationError()

//    @StateStrategyType(value = SkipStrategy::class)
//    fun initUpdateLocation()
}
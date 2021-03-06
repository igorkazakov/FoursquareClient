package com.igorkazakov.user.foursquareclient.screens.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainActivityInterface : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun showInitFragment()
}
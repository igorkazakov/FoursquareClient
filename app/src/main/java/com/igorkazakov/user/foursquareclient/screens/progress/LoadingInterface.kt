package com.igorkazakov.user.foursquareclient.screens.progress

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface LoadingInterface {

    @StateStrategyType(value = SkipStrategy::class)
    fun showLoading()

    @StateStrategyType(value = SkipStrategy::class)
    fun hideLoading()
}
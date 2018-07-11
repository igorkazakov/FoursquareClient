package com.igorkazakov.user.foursquareclient.screens.venue

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

@InjectViewState
class VenuePresenter : MvpPresenter<VenueInterface>() {

    fun setupView(model: VenueViewModel) {
        viewState.initView(model)
    }
}
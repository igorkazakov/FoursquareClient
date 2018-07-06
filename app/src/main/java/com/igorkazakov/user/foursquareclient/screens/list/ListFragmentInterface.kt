package com.igorkazakov.user.foursquareclient.screens.list

import com.arellomobile.mvp.MvpView
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel

interface ListFragmentInterface : MvpView {

    fun showVenues(venues: List<VenueViewModel>)
}
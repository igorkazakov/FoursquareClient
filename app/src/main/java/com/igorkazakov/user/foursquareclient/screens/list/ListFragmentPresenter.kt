package com.igorkazakov.user.foursquareclient.screens.list

import android.location.Location
import android.location.LocationManager
import com.arellomobile.mvp.InjectViewState
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.interactors.LocationInteractor
import com.igorkazakov.user.foursquareclient.screens.base.map.BaseMapPresenter
import io.reactivex.android.schedulers.AndroidSchedulers


@InjectViewState
class ListFragmentPresenter(private val mService: DataService,
                            locationManager: LocationManager,
                            private var locationInteractor: LocationInteractor) :
        BaseMapPresenter<ListFragmentInterface>(locationManager) {

    override fun locationChanged(location: Location) {
        loadData(locationToString(location))
        locationInteractor.postLocation(location)
    }

    private fun loadData(latLng: String) {

        mService.loadVenueRecommendations(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{ viewState.showLoading() }
                .doOnTerminate {  }
                .subscribe({
                    viewState.showVenues(it)
                    Thread.sleep(3000)
                    viewState.hideLoading()

                }, {
                    it.printStackTrace()
                })
    }

    fun showVenueDetail(model: VenueViewModel) {
        viewState.showVenueActivity(model)
    }
}
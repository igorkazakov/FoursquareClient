package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.MutableLiveData
import android.location.Location
import android.location.LocationManager
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class ListFragmentViewModel(application: MyApplication,
                            locationManager: LocationManager) : BaseMapViewModel(application, locationManager) {

    var mLocationLiveData: MutableLiveData<List<VenueViewModel>> = MutableLiveData()

//    fun getLocationLiveData() {
//
//        if (mLocationLiveData == null) {
//            mLocationLiveData = MutableLiveData()
//
//        }
//
//
//    }

    override fun locationChanged(location: Location) {
        loadData(locationToString(location))
    }

    private fun loadData(latLng: String) {

        DataService.instance.loadVenueRecommendations(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe{ viewState.showLoading() }
                //.doOnTerminate { viewState.hideLoading() }
                .subscribe({
                    //viewState.showVenues(it)
                    mLocationLiveData.value = it

                }, {
                    it.printStackTrace()
                })
    }
}
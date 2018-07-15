package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.utils.LocationUtils
import io.reactivex.android.schedulers.AndroidSchedulers

class ListFragmentViewModel(application: MyApplication) : AndroidViewModel(application) {

    var venuesLiveData: MutableLiveData<List<VenueViewModel>> = MutableLiveData()

//    fun getLocationLiveData() {
//
//        if (locationLiveData == null) {
//            locationLiveData = MutableLiveData()
//
//        }
//
//
//    }

//    override fun locationChanged(location: Location) {
//        loadData(locationToString(location))
//    }

    fun loadData(location: Location) {

        DataService.instance.loadVenueRecommendations(LocationUtils.locationToString(location))
                .observeOn(AndroidSchedulers.mainThread())
                //.doOnSubscribe{ showLoading() }
                //.doOnTerminate { hideLoading() }
                .subscribe({
                    //viewState.showVenues(it)
                    venuesLiveData.value = it

                }, {
                    it.printStackTrace()
                })
    }
}
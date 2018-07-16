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
    var
    private var mLocation: Location? = null

    fun isLocationChanged(location: Location): Boolean {

        var result = true

        mLocation?.let {
            result = location.distanceTo(it) >= 100
        }

        mLocation = location
        return result
    }

    fun loadData(location: Location) {

        DataService.instance.loadVenueRecommendations(LocationUtils.locationToString(location))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    venuesLiveData.value = it

                }, {
                    it.printStackTrace()
                })
    }
}
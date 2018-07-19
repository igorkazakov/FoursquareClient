package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import io.reactivex.android.schedulers.AndroidSchedulers

class MapFragmentVewModel(application: MyApplication,
                          private var mService: DataService) : AndroidViewModel(application) {

    var venuesLiveData: MutableLiveData<List<Venue>> = MutableLiveData()

    fun loadData(latLng: Location) {

        mService.loadVenues(latLng)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    venuesLiveData.value = it

                }, {
                    it.printStackTrace()
                })
    }
}
package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.server.DataService
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class ListFragmentViewModel(application: MyApplication,
                            private var mService: DataService) : AndroidViewModel(application) {

    var venuesLiveData: MutableLiveData<List<VenueViewModel>> = MutableLiveData()

    fun loadData(location: Location) {

        mService.loadVenueRecommendations(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    venuesLiveData.value = it

                }, {
                    it.printStackTrace()
                })
    }
}
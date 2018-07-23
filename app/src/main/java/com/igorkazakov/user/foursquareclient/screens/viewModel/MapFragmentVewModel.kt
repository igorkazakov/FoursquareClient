package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.screens.iteractor.ShowVenuesOnMapInteractor

class MapFragmentVewModel(application: MyApplication,
                          showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) : AndroidViewModel(application) {

    var venuesLiveData: MutableLiveData<VenueMapModel> = MutableLiveData()

    init {
        showVenuesOnMapInteractor.getLocationUpdates().subscribe {
            venuesLiveData.value = it
        }
    }
}
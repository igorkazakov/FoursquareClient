package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.RepositoryInterface
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.screens.iteractor.ShowVenuesOnMapInteractor
import io.reactivex.android.schedulers.AndroidSchedulers

class ListFragmentViewModel(application: MyApplication,
                            private var mRepository: RepositoryInterface,
                            private var showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) : AndroidViewModel(application) {

    var venuesLiveData: MutableLiveData<List<VenueViewModel>> = MutableLiveData()

    fun loadData(location: Location) {

        mRepository.loadVenueRecommendations(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    val models = mutableListOf<VenueViewModel>()
                    it.forEach {
                        models.add(VenueViewModel(it))
                    }

                    venuesLiveData.value = models

                    showVenuesOnMapInteractor.postLocation(VenueMapModel(location, it))

                }, {
                    it.printStackTrace()
                })
    }
}
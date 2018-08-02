package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.*
import android.location.Location
import android.support.v4.app.Fragment
import android.util.Log
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.RepositoryInterface
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.screens.iteractor.ShowVenuesOnMapInteractor
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ListFragmentViewModel(application: MyApplication,
                            private var mRepository: RepositoryInterface,
                            private var showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) :
        AndroidViewModel(application) {

    var venuesLiveData: MutableLiveData<List<VenueViewModel>> = MutableLiveData()

    fun needData() : Boolean {

        if (venuesLiveData.value == null) {
            return true

        } else  {
            return venuesLiveData.value!!.isEmpty()
        }
    }

    fun loadData(location: Location, fragment: Fragment) {

        mRepository.loadVenueRecommendations(location)
                .bindToLifecycle(fragment)
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
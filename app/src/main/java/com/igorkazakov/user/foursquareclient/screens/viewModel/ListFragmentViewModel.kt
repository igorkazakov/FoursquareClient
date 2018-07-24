package com.igorkazakov.user.foursquareclient.screens.viewModel

import android.arch.lifecycle.*
import android.location.Location
import com.igorkazakov.user.foursquareclient.application.MyApplication
import com.igorkazakov.user.foursquareclient.data.RepositoryInterface
import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import com.igorkazakov.user.foursquareclient.screens.iteractor.ShowVenuesOnMapInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ListFragmentViewModel(application: MyApplication,
                            private var mRepository: RepositoryInterface,
                            private var showVenuesOnMapInteractor: ShowVenuesOnMapInteractor) :
        AndroidViewModel(application),
        LifecycleObserver {

    var venuesLiveData: MutableLiveData<List<VenueViewModel>> = MutableLiveData()

    private var mDisposable: Disposable? = null

    fun needData() : Boolean {

        if (venuesLiveData.value == null) {
            return true

        } else  {
            return venuesLiveData.value!!.isEmpty()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun viewDestroyed() {
        mDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun loadData(location: Location) {

        mRepository.loadVenueRecommendations(location)
                .doOnSubscribe {
                    mDisposable = it
                }
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
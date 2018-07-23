package com.igorkazakov.user.foursquareclient.screens.iteractor

import com.igorkazakov.user.foursquareclient.data.view.model.VenueMapModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class ShowVenuesOnMapInteractor {

    private var mLocationSubject: Subject<VenueMapModel> = BehaviorSubject.create()

    fun getLocationUpdates() : Subject<VenueMapModel> {
        return mLocationSubject
    }

    fun postLocation(model: VenueMapModel) {
        mLocationSubject.onNext(model)
    }
}
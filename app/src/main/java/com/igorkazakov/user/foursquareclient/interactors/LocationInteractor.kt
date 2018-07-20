package com.igorkazakov.user.foursquareclient.interactors

import android.location.Location
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class LocationInteractor {

    private var mLocationSubject: Subject<Location> = BehaviorSubject.create()

    fun getLocationUpdates() : Subject<Location> {
        return mLocationSubject
    }

    fun postLocation(location: Location) {
        mLocationSubject.onNext(location)
    }
}
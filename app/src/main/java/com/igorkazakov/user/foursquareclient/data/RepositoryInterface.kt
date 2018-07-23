package com.igorkazakov.user.foursquareclient.data

import android.location.Location
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import io.reactivex.Observable

interface RepositoryInterface {

    fun loadVenueRecommendations(location: Location) : Observable<List<Venue>>
}
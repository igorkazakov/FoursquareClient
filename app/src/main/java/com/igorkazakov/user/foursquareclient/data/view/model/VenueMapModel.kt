package com.igorkazakov.user.foursquareclient.data.view.model

import android.location.Location
import com.igorkazakov.user.foursquareclient.data.server.model.Venue

class VenueMapModel(var myLocation: Location,
                    var venues: List<Venue>) {
}
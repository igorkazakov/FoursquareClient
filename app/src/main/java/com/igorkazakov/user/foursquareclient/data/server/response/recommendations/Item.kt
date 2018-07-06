package com.igorkazakov.user.foursquareclient.data.server.response.recommendations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.igorkazakov.user.foursquareclient.data.server.model.Venue

class Item {

    @SerializedName("venue")
    @Expose
    var venue: Venue? = null
}
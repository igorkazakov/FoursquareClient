package com.igorkazakov.user.foursquareclient.data.server.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Location {

    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("lat")
    @Expose
    var lat: Double? = null
    @SerializedName("lng")
    @Expose
    var lng: Double? = null
    @SerializedName("distance")
    @Expose
    var distance: Int? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null

}
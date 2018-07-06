package com.igorkazakov.user.foursquareclient.data.server.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Venue {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("location")
    @Expose
    var location: Location? = null
    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = null

}
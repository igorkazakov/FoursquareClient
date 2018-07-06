package com.igorkazakov.user.foursquareclient.data.server.response.recommendations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Group {

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null
}
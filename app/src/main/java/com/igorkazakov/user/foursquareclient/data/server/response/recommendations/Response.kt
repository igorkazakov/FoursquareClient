package com.igorkazakov.user.foursquareclient.data.server.response.recommendations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {

    @SerializedName("groups")
    @Expose
    var groups: List<Group>? = null
}
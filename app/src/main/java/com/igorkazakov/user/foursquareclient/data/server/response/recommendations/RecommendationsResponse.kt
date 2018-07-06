package com.igorkazakov.user.foursquareclient.data.server.response.recommendations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecommendationsResponse {

    @SerializedName("response")
    @Expose
    var response: Response? = null
}
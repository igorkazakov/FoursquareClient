package com.igorkazakov.user.foursquareclient.data.server.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Icon {

    @SerializedName("prefix")
    @Expose
    var prefix: String? = null
    @SerializedName("suffix")
    @Expose
    var suffix: String? = null

}
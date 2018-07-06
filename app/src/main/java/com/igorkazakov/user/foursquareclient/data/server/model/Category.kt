package com.igorkazakov.user.foursquareclient.data.server.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Category {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("pluralName")
    @Expose
    var pluralName: String? = null
    @SerializedName("icon")
    @Expose
    var icon: Icon? = null
    @SerializedName("primary")
    @Expose
    var primary: Boolean? = null

}
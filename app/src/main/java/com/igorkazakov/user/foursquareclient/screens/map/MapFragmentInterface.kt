package com.igorkazakov.user.foursquareclient.screens.map

import android.location.Location
import com.igorkazakov.user.foursquareclient.screens.base.BaseMapInterface

interface MapFragmentInterface : BaseMapInterface {

    fun showMyLocation(latLng: Location)
}
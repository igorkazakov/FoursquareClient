package com.igorkazakov.user.foursquareclient.utils

import android.location.Location

object LocationUtils {

    fun locationToString(location: Location): String {
        return "${location.latitude}, ${location.longitude}"
    }
}
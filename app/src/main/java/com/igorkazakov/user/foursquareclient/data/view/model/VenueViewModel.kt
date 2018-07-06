package com.igorkazakov.user.foursquareclient.data.view.model

import com.igorkazakov.user.foursquareclient.data.server.model.Venue

class VenueViewModel(val model: Venue) {

    fun getName() : String {
        return model.name ?: "Место"
    }

    fun getDistance() : String {
        model.location?.distance?.let {
            return "В радиусе $it метра"
        }

        return "Где то недалеко"
    }

    fun getAddress() : String {
        var address = ""
        model.location?.city?.let {
            address = it
        }

        model.location?.address?.let {
            address += " $it"
        }

        return address
    }

    fun getCategory() : String {
        return model.categories?.first()?.pluralName ?: "Без категории"
    }
}
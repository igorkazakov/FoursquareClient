package com.igorkazakov.user.foursquareclient.data.view.model

import android.os.Parcel
import android.os.Parcelable
import com.igorkazakov.user.foursquareclient.data.server.model.Venue

class VenueViewModel() : Parcelable {

    var name: String? = null
    var address: String? = null
    var category: String? = null
    var distance: String? = null

    constructor(model: Venue) : this() {

        name = model.name ?: "Место"

        distance = "Где то недалеко"

        model.location?.distance?.let {
            distance = "В радиусе $it метра"
        }

        model.location?.city?.let {
            address = "$it "
        }

        model.location?.address?.let {
            address += it
        }

        category = model.categories?.first()?.pluralName ?: "Без категории"
    }

    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(category)
        parcel.writeString(distance)
    }

    private fun readFromParcel(parcel: Parcel) {
        name = parcel.readString()
        address = parcel.readString()
        category = parcel.readString()
        distance = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VenueViewModel> {
        override fun createFromParcel(parcel: Parcel): VenueViewModel {
            return VenueViewModel(parcel)
        }

        override fun newArray(size: Int): Array<VenueViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
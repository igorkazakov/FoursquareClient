package com.igorkazakov.user.foursquareclient.data.server

import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import com.igorkazakov.user.foursquareclient.data.view.model.VenueViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DataService private constructor() {

    private object HOLDER {
        val INSTANCE = DataService()
    }

    companion object {
        val instance: DataService by lazy { HOLDER.INSTANCE }
    }

    private val baseUrl = "https://api.foursquare.com/v2/"
    private val clientId = "IGHIZ4HGE241PW53BP1JGIYEGD331WPFBMBIAWWVPBORM1WR"
    private val clientSecret = "VQIS3F345XFQRHR004RS2BA4QY2ZG5SEFRFCH4KTSKGXCZUV"
    private val versionApi = "20180323"
    private val radius = 500.0

    val service: FoursquareApi by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(FoursquareApi::class.java);
    }

    fun loadVenueRecommendations(latitudeLongitude: String) : Observable<List<VenueViewModel>> {

        return service.venueRecommendations(clientId, clientSecret, radius, latitudeLongitude, versionApi)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    val models = mutableListOf<VenueViewModel>()
                    it.response?.groups?.get(0)?.items?.forEach {
                        it.venue?.let {
                            models.add(VenueViewModel(it))
                        }
                    }

                    Observable.fromArray(models)
                }
    }

    fun loadVenues(latitudeLongitude: String) : Observable<List<Venue>> {

        return service.venueRecommendations(clientId, clientSecret, radius, latitudeLongitude, versionApi)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    val models = mutableListOf<Venue>()
                    it.response?.groups?.get(0)?.items?.forEach {
                        it.venue?.let {
                            models.add(it)
                        }
                    }

                    Observable.fromArray(models)
                }
    }
}
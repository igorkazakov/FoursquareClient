package com.igorkazakov.user.foursquareclient.data.server

import android.location.Location
import com.igorkazakov.user.foursquareclient.data.server.model.Venue
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository private constructor() : RepositoryInterface {

    private object HOLDER {
        val INSTANCE = Repository()
    }

    companion object {
        val instance: Repository by lazy { HOLDER.INSTANCE }
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

    private fun locationToString(location: Location): String {
        return "${location.latitude}, ${location.longitude}"
    }

    override fun loadVenueRecommendations(location: Location) : Observable<List<Venue>> {

        val latLngString = locationToString(location)

        return service.venueRecommendations(clientId, clientSecret, radius, latLngString, versionApi)
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
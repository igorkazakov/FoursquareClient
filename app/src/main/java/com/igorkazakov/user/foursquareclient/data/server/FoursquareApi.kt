package com.igorkazakov.user.foursquareclient.data.server

import com.igorkazakov.user.foursquareclient.data.server.response.recommendations.RecommendationsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {

    @GET("venues/explore?")
    fun venueRecommendations(@Query("client_id") clientId: String,
                                    @Query("client_secret") clientSecret: String,
                                    @Query("radius") radius: Double,
                                    @Query("ll") latitudeLongitude: String,
                                    @Query("v") version: String): Observable<RecommendationsResponse>

}
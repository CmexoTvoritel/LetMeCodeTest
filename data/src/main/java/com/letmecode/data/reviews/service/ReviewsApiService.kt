package com.letmecode.data.reviews.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewsApiService {

    @GET("/svc/topstories/v2/{section}.json")
    suspend fun getReviewsList(
        @Path("section") section: String,
        @Query("api-key") apiKey: String
    ): ResponseBody
}
package com.letmecode.data

import com.letmecode.data.reviews.service.ReviewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    fun provideReviewsApiService(): ReviewsApiService {
        return buildRetrofitObject()
            .create(ReviewsApiService::class.java)
    }

    private fun buildRetrofitObject() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        const val BASE_URL = "https://api.nytimes.com/"
    }
}
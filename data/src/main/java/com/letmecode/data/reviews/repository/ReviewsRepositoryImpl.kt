package com.letmecode.data.reviews.repository

import com.google.gson.Gson
import com.letmecode.data.reviews.model.ReviewsModelDto
import com.letmecode.data.reviews.model.toDomain
import com.letmecode.data.reviews.service.ReviewsApiService
import com.letmecode.domain.model.ReviewsModel
import com.letmecode.domain.repository.ReviewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReviewsRepositoryImpl @Inject constructor(
    private val service: ReviewsApiService
): ReviewsRepository {

    override fun getReviewsInfo(): Flow<ReviewsModel> {
        return flow {
            val response = service.getReviewsList(apiKey = API_KEY, section = SECTION)
            val responseString = response.string()
            emit(convertJsonToModel(gson = responseString).toDomain())
        }.flowOn(Dispatchers.IO).catch { exception ->
            emit(ReviewsModelDto(status = "INTERNET ERROR").toDomain())
        }
    }

    private fun convertJsonToModel(
        gson: String
    ): ReviewsModelDto = Gson().fromJson(gson, ReviewsModelDto::class.java)

    companion object {
        const val API_KEY = "AV2BaRM7QYcngB1RE90Dq5os630dutUQ"
        const val SECTION = "world"
    }
}
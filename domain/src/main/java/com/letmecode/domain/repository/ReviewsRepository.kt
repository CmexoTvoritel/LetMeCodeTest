package com.letmecode.domain.repository

import com.letmecode.domain.model.ReviewsModel
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {

    fun getReviewsInfo(): Flow<ReviewsModel>
}
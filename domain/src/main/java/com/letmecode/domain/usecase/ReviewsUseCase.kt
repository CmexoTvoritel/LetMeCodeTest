package com.letmecode.domain.usecase

import com.letmecode.domain.repository.ReviewsRepository
import javax.inject.Inject

class ReviewsUseCase @Inject constructor(
    private val repository: ReviewsRepository
) {

    operator fun invoke() = repository.getReviewsInfo()
}
package com.letmecode.testapplication.presentation.fragments.reviews.model

data class ReviewsUIState (
    val loadingState: LoadingState = LoadingState.LOADING,
    val reviews: List<ReviewUIModel>? = null
)

sealed interface ReviewsAction {
    data object OpenCalendarDialog: ReviewsAction
}

sealed interface ReviewsEvent {
    data object LoadData: ReviewsEvent
    data object DatePickerClicked: ReviewsEvent
    data class SortByDate(val day: Int, val month: Int, val year: Int): ReviewsEvent
    data class SearchReviews(val query: String): ReviewsEvent
    data object ClearDate: ReviewsEvent
    data object ClearQuery: ReviewsEvent
}
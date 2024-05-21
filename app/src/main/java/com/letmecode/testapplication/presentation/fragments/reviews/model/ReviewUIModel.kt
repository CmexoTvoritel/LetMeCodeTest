package com.letmecode.testapplication.presentation.fragments.reviews.model

data class ReviewUIModel (
    val title: String,
    val abstract: String,
    val byline: String,
    val publishedDay: String,
    val image: String,
    val url: String,
)

enum class LoadingState {
    SUCCESS,
    FAILED,
    LOADING
}
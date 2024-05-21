package com.letmecode.testapplication.presentation.fragments.reviews.viewmodel

import androidx.lifecycle.viewModelScope
import com.letmecode.core.base.viewmodel.BaseViewModel
import com.letmecode.core.extensions.convertDateToStr
import com.letmecode.domain.model.NewsResult
import com.letmecode.domain.usecase.ReviewsUseCase
import com.letmecode.testapplication.presentation.fragments.reviews.model.LoadingState
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewUIModel
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewsAction
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewsEvent
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewsUIState
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScoped
class ReviewsViewModel @Inject constructor(
    private val reviewsUseCase: ReviewsUseCase
): BaseViewModel<ReviewsUIState, ReviewsAction, ReviewsEvent>() {

    private var staticReviewsList: List<ReviewUIModel>? = null
    private var reviewsList: MutableList<ReviewUIModel>? = null
    private var selectedDate: String? = null
    private var query: String? = null

    private fun loadData() = viewModelScope.launch {
        updateViewState(state = ReviewsUIState(loadingState = LoadingState.LOADING))
        reviewsUseCase().collect { info ->
            when(info.status) {
                "OK" -> setReviewList(info = info.results)
                else -> throwError()
            }
        }
    }

    private fun setReviewList(info: List<NewsResult>) {
        val needList = info.map {
            ReviewUIModel(
                title = it.title,
                abstract = it.abstract,
                byline = it.byline,
                publishedDay = it.publishedDate,
                image = if (it.multimedia.isNotEmpty()) it.multimedia[1].url else "",
                url = it.url
            )
        }
        staticReviewsList = needList.toMutableList()
        reviewsList = if(query == null) needList.toMutableList()
            else
                filterReviewsByQuery(reviewsList = needList)
        reviewsList = if(selectedDate == null) reviewsList
            else filterReviewsByDate(reviewsList = reviewsList)
        updateViewState(state = ReviewsUIState(
            loadingState = LoadingState.SUCCESS,
            reviews = reviewsList
        ))
    }

    private fun throwError() {
        updateViewState(state = ReviewsUIState(loadingState = LoadingState.FAILED))
    }

    private fun searchReviews(query: String) {
        this.query = query
        reviewsList = filterReviewsByQuery(reviewsList = staticReviewsList)
        if(!selectedDate.isNullOrEmpty()) {
            reviewsList = filterReviewsByDate(reviewsList = reviewsList)
        }
        updateViewState(
            state = ReviewsUIState(
                loadingState = LoadingState.SUCCESS,
                reviews = reviewsList
            )
        )
    }

    private fun sortListByDate(day: Int, month: Int, year: Int) {
        val dayStr = day.convertDateToStr()
        val monthStr = month.convertDateToStr()
        val dateStr = "$year-$monthStr-$dayStr"
        selectedDate = dateStr
        reviewsList = filterReviewsByDate(reviewsList = staticReviewsList)
        if(!query.isNullOrEmpty())
            reviewsList = filterReviewsByQuery(reviewsList = reviewsList)
        updateViewState(
            state = ReviewsUIState(
                loadingState = LoadingState.SUCCESS,
                reviews = reviewsList
            )
        )
    }

    private fun clearQuery() {
        query = null
        reviewsList = staticReviewsList?.toMutableList()
        if(!selectedDate.isNullOrEmpty()) {
            reviewsList = filterReviewsByDate(reviewsList = reviewsList)
        }
        updateViewState(
            state = ReviewsUIState(
                loadingState = LoadingState.SUCCESS,
                reviews = reviewsList
            )
        )
    }

    private fun clearDate() {
        selectedDate = null
        reviewsList = staticReviewsList?.toMutableList()
        if(!query.isNullOrEmpty())
            reviewsList = filterReviewsByQuery(reviewsList = reviewsList)
        updateViewState(
            state = ReviewsUIState(
                loadingState = LoadingState.SUCCESS,
                reviews = reviewsList
            )
        )
    }

    private fun filterReviewsByDate(reviewsList: List<ReviewUIModel>?): MutableList<ReviewUIModel>? {
        return reviewsList?.filter {
            it.publishedDay.substring(0, it.publishedDay.indexOf('T')) == selectedDate
        }?.toMutableList()
    }

    private fun filterReviewsByQuery(reviewsList: List<ReviewUIModel>?): MutableList<ReviewUIModel>? {
        return reviewsList?.filter {
            it.title.contains(query!!, true) || it.abstract.contains(query!!, true)
        }?.toMutableList()
    }

    override fun obtainEvent(viewEvent: ReviewsEvent) {
        when(viewEvent) {
            is ReviewsEvent.LoadData -> loadData()
            is ReviewsEvent.DatePickerClicked -> sendViewAction(
                action = ReviewsAction.OpenCalendarDialog
            )
            is ReviewsEvent.SortByDate -> sortListByDate(
                day = viewEvent.day,
                month = viewEvent.month,
                year = viewEvent.year
            )
            is ReviewsEvent.ClearDate -> clearDate()
            is ReviewsEvent.SearchReviews -> searchReviews(query = viewEvent.query)
            is ReviewsEvent.ClearQuery -> clearQuery()
        }
    }
}
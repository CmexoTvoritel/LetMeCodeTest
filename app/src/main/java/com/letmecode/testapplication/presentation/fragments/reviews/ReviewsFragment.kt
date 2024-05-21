package com.letmecode.testapplication.presentation.fragments.reviews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.letmecode.core.base.ViewBindingBaseFragment
import com.letmecode.core.extensions.beGone
import com.letmecode.core.extensions.beVisible
import com.letmecode.core.extensions.beVisibleIf
import com.letmecode.core.extensions.convertDateToStr
import com.letmecode.core.extensions.repeatOnStarted
import com.letmecode.testapplication.databinding.FragmentReviewsBinding
import com.letmecode.testapplication.presentation.fragments.reviews.adapter.ReviewsAdapter
import com.letmecode.testapplication.presentation.fragments.reviews.model.LoadingState
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewsAction
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewsEvent
import com.letmecode.testapplication.presentation.fragments.reviews.viewmodel.ReviewsViewModel
import com.letmecode.testapplication.presentation.helpers.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsFragment : ViewBindingBaseFragment<FragmentReviewsBinding>() {

    @Inject
    lateinit var viewModel: ReviewsViewModel

    @Inject
    lateinit var dialogHelper: DialogHelper

    private var adapter = ReviewsAdapter()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReviewsBinding
        get() = FragmentReviewsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setupStates()
        setupActions()
    }

    private fun setupRV() = binding.apply {
        frRVReviews.layoutManager = LinearLayoutManager(context)
        frRVReviews.adapter = adapter
        adapter.clickCallback = { item ->
            if(item.url.isNotEmpty())
                openUrlIntent(url = item.url)
        }
    }

    private fun setupStates() = binding.apply {
        viewModel.obtainEvent(viewEvent = ReviewsEvent.LoadData)
        repeatOnStarted {
            viewModel.viewStates.collect { state ->
                if(state == null)
                    return@collect
                when(state.loadingState) {
                    LoadingState.LOADING -> {
                        frRefreshContainer.isRefreshing = true
                        adapter.submitList(listOf())
                        ftErrorText.beGone()
                    }
                    LoadingState.SUCCESS -> {
                        frRefreshContainer.isRefreshing = false
                        adapter.submitList(state.reviews)
                        ftErrorText.beGone()
                    }
                    LoadingState.FAILED -> {
                        showErrorDialog()
                        frRefreshContainer.isRefreshing = false
                        ftErrorText.beVisible()
                    }
                }
            }
        }
    }

    private fun setupActions() = binding.apply {
        frRefreshContainer.setOnRefreshListener {
            viewModel.obtainEvent(viewEvent = ReviewsEvent.LoadData)
        }
        frDateContainer.setOnClickListener {
            viewModel.obtainEvent(viewEvent = ReviewsEvent.DatePickerClicked)
        }
        frSearchEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                frSearchClear.beVisibleIf(condition = text.toString().isNotEmpty())
                viewModel.obtainEvent(viewEvent = ReviewsEvent.SearchReviews(query = text.toString()))
            }

            override fun afterTextChanged(p0: Editable?) { }
        })
        frSearchClear.setOnClickListener {
            frSearchEditText.setText("")
            viewModel.obtainEvent(viewEvent = ReviewsEvent.ClearQuery)
        }
        frDateClear.setOnClickListener {
            ftDateText.text = ""
            frDateClear.beGone()
            viewModel.obtainEvent(viewEvent = ReviewsEvent.ClearDate)
        }
        repeatOnStarted {
            viewModel.viewActions.collect { action ->
                when(action) {
                    is ReviewsAction.OpenCalendarDialog -> openCalendarDialog()
                }
            }
        }
    }

    private fun openUrlIntent(url: String) {
        Intent(Intent.ACTION_VIEW).also {
            it.data = Uri.parse(url)
            activity?.startActivity(it)
        }
    }

    private fun showErrorDialog() {
        dialogHelper.showInternetErrorDialog()
    }

    private fun openCalendarDialog() = binding.apply {
        dialogHelper.showCalendarForSearch { day, month, year ->
            val dayStr = day.convertDateToStr()
            val monthStr = month.convertDateToStr()
            val dateStr = "$year / $monthStr / $dayStr"
            ftDateText.text = dateStr
            frDateClear.beVisible()
            viewModel.obtainEvent(viewEvent = ReviewsEvent.SortByDate(
                day = day,
                month = month,
                year = year
            ))
        }
    }

}
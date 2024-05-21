package com.letmecode.testapplication.presentation.fragments.reviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.letmecode.testapplication.databinding.ItemReviewLayoutBinding
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewUIModel
import com.letmecode.testapplication.presentation.fragments.reviews.viewholder.ReviewsViewHolder

class ReviewsAdapter: ListAdapter<ReviewUIModel, ReviewsViewHolder>(ReviewsDiffUtils()) {

    var clickCallback: ((item: ReviewUIModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        return ReviewsViewHolder(binding = ItemReviewLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(item = currentList[position])
        holder.clickCallback = clickCallback
    }
}
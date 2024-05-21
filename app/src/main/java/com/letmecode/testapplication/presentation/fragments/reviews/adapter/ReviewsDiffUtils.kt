package com.letmecode.testapplication.presentation.fragments.reviews.adapter

import androidx.recyclerview.widget.DiffUtil
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewUIModel

class ReviewsDiffUtils: DiffUtil.ItemCallback<ReviewUIModel>() {
    override fun areItemsTheSame(oldItem: ReviewUIModel, newItem: ReviewUIModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReviewUIModel, newItem: ReviewUIModel): Boolean {
        return oldItem.title == newItem.title && oldItem.abstract == newItem.abstract &&
                oldItem.byline == newItem.byline && oldItem.image == newItem.image &&
                oldItem.publishedDay == newItem.publishedDay
    }
}
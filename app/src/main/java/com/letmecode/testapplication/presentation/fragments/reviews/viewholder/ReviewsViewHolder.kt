package com.letmecode.testapplication.presentation.fragments.reviews.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letmecode.testapplication.databinding.ItemReviewLayoutBinding
import com.letmecode.testapplication.presentation.fragments.reviews.model.ReviewUIModel

class ReviewsViewHolder(
    private val binding: ItemReviewLayoutBinding
): RecyclerView.ViewHolder(binding.root) {

    var clickCallback: ((item: ReviewUIModel) -> Unit)? = null

    fun bind(item: ReviewUIModel) = binding.apply {
        irlReviewTitle.text = item.title
        irlReviewDescription.text = item.abstract
        irlReviewAuthor.text = item.byline.ifEmpty { "No author" }
        irlReviewDate.text = item.publishedDay.substring(0, item.publishedDay.indexOf('T'))
        irlReviewTime.text = item.publishedDay.substring(
            item.publishedDay.indexOf('T') + 1,
            item.publishedDay.indexOf('-', item.publishedDay.indexOf('T') + 1)
        )
        Glide.with(binding.root.context)
            .load(item.image)
            .centerCrop()
            .into(irlReviewImage)
        irlOpenReview.setOnClickListener {
            clickCallback?.invoke(item)
        }
    }
}
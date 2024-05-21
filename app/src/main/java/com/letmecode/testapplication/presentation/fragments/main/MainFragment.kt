package com.letmecode.testapplication.presentation.fragments.main

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.letmecode.core.base.ViewBindingBaseFragment
import com.letmecode.testapplication.presentation.fragments.critics.CriticsFragment
import com.letmecode.testapplication.resources.R
import com.letmecode.testapplication.databinding.FragmentMainBinding
import com.letmecode.testapplication.presentation.fragments.main.adapter.MainViewPagerAdapter
import com.letmecode.testapplication.presentation.fragments.main.model.TabType
import com.letmecode.testapplication.presentation.fragments.reviews.ReviewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : ViewBindingBaseFragment<FragmentMainBinding>() {

    private val fragmentsList = listOf(
        ReviewsFragment(),
        CriticsFragment()
    )

    private lateinit var viewPagerAdapter: MainViewPagerAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupActions()
    }

    private fun setupViewPager() = binding.apply {
        viewPagerAdapter = MainViewPagerAdapter(this@MainFragment, fragmentsList)
        fmViewPager.adapter = viewPagerAdapter
        fmViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> setActiveTab(
                        activeTab = fmReviewsTab,
                        inactiveTab = fmCriticsTab,
                        tabType = TabType.Reviews
                    )
                    1 -> setActiveTab(
                        activeTab = fmCriticsTab,
                        inactiveTab = fmReviewsTab,
                        tabType = TabType.Critics
                    )
                }
            }
        })
    }

    private fun setupActions() = binding.apply {
        fmReviewsTab.setOnClickListener {
            fmViewPager.currentItem = 0
            setActiveTab(
                activeTab = fmReviewsTab,
                inactiveTab = fmCriticsTab,
                tabType = TabType.Reviews
            )
        }
        fmCriticsTab.setOnClickListener {
            fmViewPager.currentItem = 1
            setActiveTab(
                activeTab = fmCriticsTab,
                inactiveTab = fmReviewsTab,
                tabType = TabType.Critics
            )
        }
    }

    private fun setActiveTab(activeTab: TextView, inactiveTab: TextView, tabType: TabType) {
        activeTab.setTypeface(null, Typeface.BOLD)
        activeTab.setTextColor(requireContext().getColor(
            if(tabType == TabType.Critics) R.color.primary_critics
            else R.color.primary_reviews
        ))
        activeTab.setBackgroundColor(requireContext().getColor(R.color.white))
        inactiveTab.setTypeface(null, Typeface.NORMAL)
        inactiveTab.setTextColor(requireContext().getColor(R.color.white))
        inactiveTab.setBackgroundColor(requireContext().getColor(
            if(tabType == TabType.Critics) R.color.primary_critics
            else R.color.primary_reviews
        ))
        binding.fmTopContainer.setBackgroundColor(requireContext().getColor(
            if(tabType == TabType.Critics) R.color.primary_critics
            else R.color.primary_reviews
        ))
        activity?.window?.statusBarColor = requireContext().getColor(
            if(tabType == TabType.Critics) R.color.primary_critics
            else R.color.primary_reviews
        )
        binding.fmPageTitle.text = requireContext().getString(
            if(tabType == TabType.Critics) R.string.fragment_main_critics_title
            else R.string.fragment_main_reviews_title
        )
    }
}
package com.letmecode.testapplication.presentation.fragments.critics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.letmecode.core.base.ViewBindingBaseFragment
import com.letmecode.testapplication.databinding.FragmentCriticsBinding

class CriticsFragment : ViewBindingBaseFragment<FragmentCriticsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCriticsBinding
        get() = FragmentCriticsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
package com.letmecode.testapplication.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.letmecode.core.base.ViewBindingBaseActivity
import com.letmecode.testapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ViewBindingBaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
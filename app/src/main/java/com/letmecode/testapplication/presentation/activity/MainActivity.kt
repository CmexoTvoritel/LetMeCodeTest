package com.letmecode.testapplication.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.letmecode.core.base.ViewBindingBaseActivity
import com.letmecode.testapplication.R
import com.letmecode.testapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ViewBindingBaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavController()
        setupNavGraph(savedInstanceState = savedInstanceState)
    }

    private fun setupNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(
            R.id.amFragmentContainer
        ) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupNavGraph(savedInstanceState: Bundle?) {
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.nav_graph)
        runCatching {
            navController.setGraph(graph, null)
        }.getOrNull() ?: navController.restoreState(
            savedInstanceState?.getBundle(
                KEY_RESTORE_NAV_STATE
            )
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(KEY_RESTORE_NAV_STATE, navController.saveState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navController.restoreState(savedInstanceState.getBundle(KEY_RESTORE_NAV_STATE))
    }

    companion object {
        private const val KEY_RESTORE_NAV_STATE = "nav_state"
    }
}
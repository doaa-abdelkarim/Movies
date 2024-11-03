package com.example.movies.presentation

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var favoritesItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavController()
        if ((appContext as MoviesApp).isLargeScreen)
            initTablet()
        else
            initPhone()
        observeState()
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initPhone() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailsFragment || destination.id == R.id.moviePlayerFragment)
                binding.toolbar.visibility = View.VISIBLE
            else
                binding.toolbar.visibility = View.GONE
        }
        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun initTablet() {
        changeStartDestination()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.moviesFragment, R.id.tvShowsFragment),
            binding.drawerLayout
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView?.setupWithNavController(navController)

        lookUpFavoritesItem()
    }

    private fun changeStartDestination() {
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.moviesFragment)
        navController.graph = graph
    }

    private fun lookUpFavoritesItem() {
        binding.navView?.let {
            favoritesItem = it.menu.findItem(R.id.favoritesFragment).apply {
                isVisible = false
            }
        }
    }

    private fun observeState() {
        if ((appContext as MoviesApp).isLargeScreen)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainActivityViewModel.favorites.collect {
                        favoritesItem.isVisible = it.isNotEmpty()
                    }
                }
            }
    }

}





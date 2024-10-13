package com.example.movies.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class ManActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var favoritesItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView?.let {
            changeStartDestination()
            initToolBar()
            initNavigationView()
            lookUpFavoritesItem()
            subscribeToLiveData()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun changeStartDestination() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(R.id.moviesFragment)
        navHostFragment.navController.graph = graph
    }

    private fun initToolBar() {
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.moviesFragment, R.id.tvShowsFragment),
            binding.drawerLayout
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initNavigationView() {
        binding.navView?.setupWithNavController(navController)
    }

    private fun lookUpFavoritesItem() {
        binding.navView?.let {
            favoritesItem = it.menu.findItem(R.id.favoritesFragment).apply {
                isVisible = false
            }

        }
    }

    private fun subscribeToLiveData() {
        mainActivityViewModel.favorites.observe(this, {
            favoritesItem.isVisible = !it.isNullOrEmpty()

        })
    }

}





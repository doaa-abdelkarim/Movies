package com.example.movies.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movies.presentation.MainActivityViewModel
import com.example.movies.presentation.MainActivityViewModel.MainEvent.ShowSavedMessage
import com.example.movies.presentation.details.screens.DetailsScreen
import com.example.movies.presentation.details.draft.parent.DetailsViewModel
import com.example.movies.presentation.home.screens.HomeScreen
import com.example.movies.presentation.movieplayer.screens.MoviePlayerScreen
import com.example.movies.presentation.navigation.Screen.Details
import com.example.movies.presentation.navigation.Screen.Home
import com.example.movies.presentation.navigation.Screen.MoviePlayer
import com.example.movies.util.exhaustive

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val mainActivityViewModel: MainActivityViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        mainActivityViewModel.mainEvent.collect {
            when (it) {
                is ShowSavedMessage ->
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }.exhaustive
        }
    }
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            val favorites = mainActivityViewModel.favorites.collectAsState().value
            HomeScreen(
                favorites = favorites,
                navigateToDetailsScreen = { movie ->
                    navController.navigate(
                        route = Details(
                            movieId = movie.id,
                            isMovie = movie.isMovie
                        )
                    )
                }
            )
        }
        composable<Details> {
            val detailsViewModel: DetailsViewModel = hiltViewModel()
            val movies = detailsViewModel.movie.collectAsState().value
            DetailsScreen(
                movie = movies,
                onAddToFavoriteClick = { movie -> mainActivityViewModel.onAddToFavoriteClick(movie = movie) },
                navigateToMoviePlayerScreen = { clipKey ->
                    navController.navigate(
                        route = MoviePlayer(
                            clipKey = clipKey
                        )
                    )
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable<MoviePlayer> {
            val moviePlayer = it.toRoute<MoviePlayer>()
            MoviePlayerScreen(
                clipKey = moviePlayer.clipKey,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
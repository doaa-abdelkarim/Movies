package com.example.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movies.presentation.details.DetailsScreen
import com.example.movies.presentation.details.parent.DetailsViewModel
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.movieplayer.MoviePlayerScreen
import com.example.movies.presentation.navigation.Screen.Details
import com.example.movies.presentation.navigation.Screen.Home
import com.example.movies.presentation.navigation.Screen.MoviePlayer

@Composable
fun MoviesApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
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
            val movies = detailsViewModel.movieDetails.collectAsState().value
            val favorites = detailsViewModel.favorites.collectAsState().value
            DetailsScreen(
                movie = movies,
                onAddToFavoriteClick = { detailsViewModel.onAddToFavorite() },
                navigateToMoviePlayerScreen = { clipKey ->
                    navController.navigate(
                        route = MoviePlayer(
                            clipKey = clipKey
                        )
                    )
                }
            )
        }
        composable<MoviePlayer> {
            val moviePlayer = it.toRoute<MoviePlayer>()
            MoviePlayerScreen(clipKey = moviePlayer.clipKey)
        }
    }
}
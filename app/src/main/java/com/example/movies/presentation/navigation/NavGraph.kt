package com.example.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.MovieNavType
import com.example.movies.presentation.details.DetailsScreen
import com.example.movies.presentation.home.HomeScreen
import com.example.movies.presentation.navigation.Screen.Details
import com.example.movies.presentation.navigation.Screen.Home
import com.example.movies.presentation.navigation.Screen.MoviePlayer
import com.example.movies.presentation.videoplayer.MoviePlayerScreen
import kotlin.reflect.typeOf

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
                            movie = movie.copy(
                                posterPath = movie.posterPath?.substring(1),
                                backdropPath = movie.backdropPath?.substring(1)
                            )
                        )
                    )
                }
            )
        }
        composable<Details>(
            typeMap = mapOf(
                typeOf<Movie?>() to MovieNavType
            )
        ) {
            val details = it.toRoute<Details>()
            DetailsScreen(
                movie = details.movie,
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
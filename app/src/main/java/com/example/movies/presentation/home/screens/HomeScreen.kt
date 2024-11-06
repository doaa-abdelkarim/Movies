package com.example.movies.presentation.home.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.entities.Movie
import com.example.movies.util.extensions.isLargeScreen

@Composable
fun HomeScreen(
    favorites: List<Favorite>,
    navigateToDetailsScreen: (Movie) -> Unit,
    navigateToMoviePlayerScreen: (String) -> Unit,
) {

    if (LocalContext.current.isLargeScreen()) {
        HomeTabletLayout(
            favorites = favorites,
            navigateToMoviePlayerScreen = navigateToMoviePlayerScreen
        )
    } else {
        HomePhoneLayout(
            favorites = favorites,
            navigateToDetailsScreen = navigateToDetailsScreen
        )
    }

}
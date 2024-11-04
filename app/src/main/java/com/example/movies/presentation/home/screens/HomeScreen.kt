package com.example.movies.presentation.home.screens

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.entities.Movie

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HomeScreen(
    favorites: List<Favorite>,
    navigateToDetailsScreen: (Movie) -> Unit,
    navigateToMoviePlayerScreen: (String) -> Unit,
) {

    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)

    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
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
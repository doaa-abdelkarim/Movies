package com.example.movies.presentation.home.components

import androidx.compose.runtime.Composable
import com.example.movies.domain.entities.Favorite

@Composable
fun PageFavorites(favorites: List<Favorite>) {
    ListFavorites(favorites = favorites)
}
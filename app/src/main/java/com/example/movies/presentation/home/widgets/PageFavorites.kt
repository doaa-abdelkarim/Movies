package com.example.movies.presentation.home.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.movies.domain.entities.Favorite

@Composable
fun PageFavorites(
    innerPadding: PaddingValues,
    favorites: List<Favorite>
) {
    ListFavorites(
        innerPadding= innerPadding,
        favorites = favorites
    )
}
package com.example.movies.presentation.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.domain.entities.Favorite
import com.example.movies.ui.theme.darkerGray

@Composable
fun ListFavorites(favorites: List<Favorite>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkerGray),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small))
    ) {
        itemsIndexed(favorites) { index, favorite ->
            CellFavorite(favorite = favorite)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun ListFavoritesPreview() {
    ListFavorites(
        favorites = listOf(
            Favorite(
                movieId = 1,
                posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
                title = "Deadpool & Wolverine"
            ),
            Favorite(
                movieId = 2,
                posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
                title = "Deadpool & Wolverine"
            ),
        )
    )
}
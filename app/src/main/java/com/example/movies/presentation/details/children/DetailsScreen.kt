package com.example.movies.presentation.details.children

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.movies.domain.entities.Movie

@Composable
fun DetailsScreen(movie: Movie) {
    Log.d("ppppp", "DetailsScreen: $movie")
}
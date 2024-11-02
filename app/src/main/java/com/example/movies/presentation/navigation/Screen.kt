package com.example.movies.presentation.navigation

import com.example.movies.domain.entities.Movie
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home: Screen()
    @Serializable
    data class Details(val movie: Movie?): Screen()
    @Serializable
    data class MoviePlayer(val clipKey: String): Screen()
}
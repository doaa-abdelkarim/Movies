package com.example.movies.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class Details(
        val movieId: Int? = null,
        val isMovie: Boolean? = null
    ) : Screen()

    @Serializable
    data class MoviePlayer(val clipKey: String) : Screen()
}
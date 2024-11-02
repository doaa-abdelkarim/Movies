package com.example.movies.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Favorite

@Entity(tableName = "favorites_table")
data class LocalFavorite(
    @PrimaryKey
    val movieId: Int,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val title: String? = null
)

fun List<LocalFavorite>.asDomainModel(): List<Favorite> =
    map {
        Favorite(
            movieId = it.movieId,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            title = it.title
        )
    }
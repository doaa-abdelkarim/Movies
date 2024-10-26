package com.example.movies.data.local.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Favorite

@Entity(tableName = "favorites_table")
data class LocalFavorite(
    @PrimaryKey
    val videoId: Int,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val title: String? = null
)

fun List<LocalFavorite>.asDomainModel(): List<Favorite> =
    map {
        Favorite(
            videoId = it.videoId,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            title = it.title
        )
    }
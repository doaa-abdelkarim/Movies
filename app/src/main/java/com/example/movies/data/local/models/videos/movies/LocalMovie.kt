package com.example.movies.data.local.models.videos.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.BaseLocalVideo
import com.example.movies.domain.entities.Movie

@Entity(tableName = "movies_table")
data class LocalMovie(
    @PrimaryKey
    override val id: Int,
    override val posterPath: String? = null,
    override val backdropPath: String? = null,
    override val title: String? = null,
    override val popularity: Double? = null,
    override val genres: String? = null,
    override val originalLanguage: String? = null,
    override val overview: String? = null,
    override val releaseDate: String? = null,
    val revenue: Int? = null,
    override val originalTitle: String? = null
) : BaseLocalVideo()

fun LocalMovie.asDomainModel(): Movie {
    return Movie(
        id = id,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = title,
        popularity = popularity,
        genres = genres,
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = releaseDate,
        revenue = revenue,
        originalTitle = originalTitle
    )
}

fun List<LocalMovie>.asDomainModel(): List<Movie> =
    map {
        Movie(
            id = it.id,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            title = it.title,
            popularity = it.popularity,
            genres = it.genres,
            originalLanguage = it.originalLanguage,
            overview = it.overview,
            releaseDate = it.releaseDate,
            revenue = it.revenue,
            originalTitle = it.originalTitle
        )
    }
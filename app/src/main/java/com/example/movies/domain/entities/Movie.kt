package com.example.movies.domain.entities

import com.example.movies.data.local.models.videos.movies.LocalMovie
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
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
) : Video()

fun Video.asMovieDatabaseModel(): LocalMovie {
    return LocalMovie(
        id = id,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = title,
        popularity = popularity,
        genres = genres,
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = releaseDate,
        revenue = (this as Movie).revenue,
        originalTitle = originalTitle
    )
}

fun List<Video>.asMovieDatabaseModel(): List<LocalMovie> {
    return map {
        LocalMovie(
            id = it.id,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            title = it.title,
            popularity = it.popularity,
            genres = it.genres,
            originalLanguage = it.originalLanguage,
            overview = it.overview,
            releaseDate = it.releaseDate,
            revenue = (it as Movie).revenue,
            originalTitle = it.originalTitle
        )
    }
}
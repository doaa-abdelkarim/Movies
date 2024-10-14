package com.example.movies.domain.entities

import com.example.movies.data.local.models.videos.movies.LocalMovie
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    override val id: Int? = null,
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
): Video()

fun Video.asMovieDatabaseModel(): LocalMovie {
    return LocalMovie(id, posterPath, backdropPath, title, popularity, genres,
        originalLanguage, overview, releaseDate, (this as Movie).revenue, originalTitle)
}

fun List<Video>.asMovieDatabaseModel(): List<LocalMovie> {
    return map {
            LocalMovie(it.id, it.posterPath, it.backdropPath, it.title, it.popularity, it.genres,
                it.originalLanguage, it.overview, it.releaseDate, (it as Movie).revenue, it.originalTitle
            )
        }
}
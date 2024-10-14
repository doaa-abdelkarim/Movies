package com.example.movies.domain.entities

import com.example.movies.data.local.models.videos.tvshows.LocalTVShow
import kotlinx.parcelize.Parcelize

@Parcelize
data class TVShow (
    override val id: Int? = null,
    override val posterPath: String? = null,
    override val backdropPath: String? = null,
    override val title: String? = null,
    override val popularity: Double? = null,
    override val genres: String? = null,
    override val originalLanguage: String? = null,
    override val overview: String? = null,
    override val releaseDate: String? = null,
    override val originalTitle: String? = null
): Video()

fun Video.asTVShowDatabaseModel(): LocalTVShow {
    return LocalTVShow(id, posterPath, backdropPath, title, popularity, genres,
        originalLanguage, overview, releaseDate, originalTitle)
}

fun List<Video>.asTVShowDatabaseModel(): List<LocalTVShow> {
    return map {
        LocalTVShow(it.id, it.posterPath, it.backdropPath, it.title, it.popularity, it.genres,
            it.originalLanguage, it.overview, it.releaseDate, it.originalTitle
        )
    }
}
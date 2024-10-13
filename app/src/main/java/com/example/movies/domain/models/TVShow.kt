package com.example.movies.domain.models

import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShow
import kotlinx.android.parcel.Parcelize

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

fun Video.asTVShowDatabaseModel(): DatabaseTVShow {
    return DatabaseTVShow(id, posterPath, backdropPath, title, popularity, genres,
        originalLanguage, overview, releaseDate, originalTitle)
}

fun List<Video>.asTVShowDatabaseModel(): List<DatabaseTVShow> {
    return map {
        DatabaseTVShow(it.id, it.posterPath, it.backdropPath, it.title, it.popularity, it.genres,
            it.originalLanguage, it.overview, it.releaseDate, it.originalTitle
        )
    }
}
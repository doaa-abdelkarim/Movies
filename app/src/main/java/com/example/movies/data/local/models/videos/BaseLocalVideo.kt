package com.example.movies.data.local.models.videos

import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.TVShow
import com.example.movies.domain.entities.Video

abstract class BaseLocalVideo {
    abstract val id: Int
    abstract val posterPath: String?
    abstract val backdropPath: String?
    abstract val title: String?
    abstract val popularity: Double?
    abstract val genres: String?
    abstract val originalLanguage: String?
    abstract val overview: String?
    abstract val releaseDate: String?
    abstract val originalTitle: String?
}

fun BaseLocalVideo.asDomainModel(): Video {
    return if (this is LocalMovie)
        Movie(
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
        ) else
        TVShow(
            id = id,
            posterPath = posterPath,
            backdropPath = backdropPath,
            title = title,
            popularity = popularity,
            genres = genres,
            originalLanguage = originalLanguage,
            overview = overview,
            releaseDate = releaseDate,
            originalTitle = originalTitle
        )
}

fun List<BaseLocalVideo>.asDomainModel(): List<Video> =
    if (this.isNotEmpty())
        if (this[0] is LocalMovie)
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
                    revenue = (it as LocalMovie).revenue,
                    originalTitle = it.originalTitle
                )
            }
        else
            map {
                TVShow(
                    id = it.id,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
                    title = it.title,
                    popularity = it.popularity,
                    genres = it.genres,
                    originalLanguage = it.originalLanguage,
                    overview = it.overview,
                    releaseDate = it.releaseDate,
                    originalTitle = it.originalTitle
                )
            }
    else
        emptyList()

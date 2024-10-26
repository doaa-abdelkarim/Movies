package com.example.movies.data.local.models.videos

import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.domain.entities.BaseVideo
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.TVShow

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

    /*
    I added this field to fix "RemoteMediator calls API again and again" issue according to answer
    suggested in this link until I find better solution to this issue
    https://stackoverflow.com/a/76556967
    */
    var createdAt: Long = System.currentTimeMillis()
}

fun BaseLocalVideo.asDomainModel(): BaseVideo {
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
            originalTitle = originalTitle,
            revenue = revenue
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

fun List<BaseLocalVideo>.asDomainModel(): List<BaseVideo> =
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
                    originalTitle = it.originalTitle,
                    revenue = (it as LocalMovie).revenue
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

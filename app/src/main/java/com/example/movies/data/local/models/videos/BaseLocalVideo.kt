package com.example.movies.data.local.models.videos

import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.domain.entities.BaseVideo
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.TVShow

abstract class BaseLocalVideo {
    /*
    I added this field to fix "RemoteMediator calls API again and again"
    A unique Int key which autoIncrements by 1 with every insert.
    I need this immutable key to maintain consistent item ordering so that the UI
    does not scroll out of order when new video are appended and inserted into this db.
     */
    abstract val pk: Int
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

fun BaseLocalVideo.asDomainModel(): BaseVideo {
    return if (this is LocalMovie)
        Movie(
            pk = pk,
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
            pk = pk,
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
package com.example.movies.domain.entities

import android.net.Uri
import android.os.Parcelable
import com.example.movies.data.local.models.videos.BaseLocalVideo
import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow
import com.example.movies.util.AppConstants.Companion.IMAGE_BASE_URL

abstract class BaseVideo : Parcelable {
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

    val posterUri: Uri
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(posterPath)
            .build()

    val backdropUri: Uri
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(backdropPath)
            .build()

}

fun BaseVideo.asDatabaseModel(): BaseLocalVideo {
    return if (this is Movie)
        LocalMovie(
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
        LocalTVShow(
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
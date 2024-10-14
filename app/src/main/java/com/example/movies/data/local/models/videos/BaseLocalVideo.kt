package com.example.movies.data.local.models.videos

import android.net.Uri
import com.example.movies.util.Constants.Companion.IMAGE_BASE_URL

abstract class BaseLocalVideo {
    abstract val id: Int?
    abstract val posterPath: String?
    abstract val backdropPath: String?
    abstract val title: String?
    abstract val popularity: Double?
    abstract val genres: String?
    abstract val originalLanguage: String?
    abstract val overview: String?
    abstract val releaseDate: String?
    abstract val originalTitle: String?

    val posterUri: String
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(posterPath)
            .build().toString()

    val backdropUri: String
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(backdropPath)
            .build()
            .toString()

}

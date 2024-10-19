package com.example.movies.domain.entities

import android.net.Uri
import android.os.Parcelable
import com.example.movies.util.AppConstants.Companion.IMAGE_BASE_URL

abstract class Video: Parcelable {
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

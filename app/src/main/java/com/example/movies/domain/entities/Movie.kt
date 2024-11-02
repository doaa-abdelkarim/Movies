package com.example.movies.domain.entities

import android.net.Uri
import android.os.Parcelable
import com.example.movies.util.constants.AppConstants.Companion.IMAGE_BASE_URL
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val pk: Int? = null,
    val id: Int,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val title: String? = null,
    val popularity: Double? = null,
    val genres: String? = null,
    val originalLanguage: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val originalTitle: String? = null,
    val revenue: Int? = null,
    val isMovie: Boolean
):Parcelable {
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
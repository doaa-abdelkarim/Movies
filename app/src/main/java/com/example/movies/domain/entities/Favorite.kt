package com.example.movies.domain.entities

import android.net.Uri
import com.example.movies.util.constants.AppConstants.Companion.IMAGE_BASE_URL

data class Favorite(
    val videoId: Int? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val title: String? = null
) {
    val posterUri: Uri
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(posterPath)
            .build()
}
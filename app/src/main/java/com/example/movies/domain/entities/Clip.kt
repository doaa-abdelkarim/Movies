package com.example.movies.domain.entities

import android.net.Uri
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_BASE_URL
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_HIGH_QUALITY

data class Clip(
    val videoId: Int,
    val clipId: String,
    val name: String? = null,
    val key: String? = null
) {
    val clipUri: Uri
        get() = Uri.parse(YOUTUBE_IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(key)
            .appendPath(YOUTUBE_IMAGE_HIGH_QUALITY)
            .build()
}
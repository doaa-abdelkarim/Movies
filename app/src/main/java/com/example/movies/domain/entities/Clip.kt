package com.example.movies.domain.entities

import android.net.Uri
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_BASE_URL
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_HIGH_QUALITY
import com.example.movies.util.getYouTubeUriByKey

data class Clip(
    val videoId: Int,
    val clipId: String,
    val name: String? = null,
    val key: String? = null
) {
    val clipUri: Uri?
        get() = key?.let { getYouTubeUriByKey(it)}
}
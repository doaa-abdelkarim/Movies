package com.example.movies.domain.entities

import android.net.Uri
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
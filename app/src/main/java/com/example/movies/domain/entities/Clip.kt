package com.example.movies.domain.entities

import android.net.Uri
import com.example.movies.data.local.models.videos.movies.LocalMovieClip
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowClip
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_BASE_URL
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_HIGH_QUALITY

data class Clip(
    val videoId: Int,
    val id: String,
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

fun List<Clip>.asMovieClipsDatabaseModel(): List<LocalMovieClip> {
    return map {
        LocalMovieClip(
            videoId = it.videoId,
            id = it.id,
            name = it.name,
            key = it.key,
            clipUri = it.clipUri.toString()
        )
    }
}

fun List<Clip>.asTVShowClipsDatabaseModel(): List<LocalTVShowClip> {
    return map {
        LocalTVShowClip(
            videoId = it.videoId,
            id = it.id,
            name = it.name,
            key = it.key,
            clipUri = it.clipUri.toString()
        )
    }
}
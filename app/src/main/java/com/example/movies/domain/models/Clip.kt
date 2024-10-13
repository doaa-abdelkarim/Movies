package com.example.movies.domain.models

import android.net.Uri
import com.example.movies.data.local.models.videos.movie.DatabaseMovieClip
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShowClip
import com.example.movies.util.Constants.Companion.YOUTUBE_IMAGE_BASE_URL
import com.example.movies.util.Constants.Companion.YOUTUBE_IMAGE_HIGH_QUALITY

data class Clip(
    val videoId: Int? = null,
    val id: String? = null,
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

fun List<Clip>.asMovieClipsDatabaseModel(): List<DatabaseMovieClip> {
    return map {
        DatabaseMovieClip(it.videoId, it.id?: "", it.name, it.key, it.clipUri.toString())
    }
}

fun List<Clip>.asTVShowClipsDatabaseModel(): List<DatabaseTVShowClip> {
    return map {
        DatabaseTVShowClip(it.videoId, it.id?: "", it.name, it.key, it.clipUri.toString())
    }
}
package com.example.movies.domain.entities

import com.example.movies.data.local.models.videos.movies.LocalMovieReview
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview

data class Review(
    val videoId: Int? = null,
    val id: String? = null,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun List<Review>.asMovieReviewsDatabaseModel(): List<LocalMovieReview> {
    return map {
        LocalMovieReview(it.videoId, it.id?: "", it.username, it.avatarPath, it.content)
    }
}

fun List<Review>.asTVShowReviewsDatabaseModel(): List<LocalTVShowReview> {
    return map {
        LocalTVShowReview(it.videoId, it.id?: "", it.username, it.avatarPath, it.content)
    }
}
package com.example.movies.domain.entities

import com.example.movies.data.local.models.videos.movies.LocalMovieReview
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview

data class Review(
    val videoId: Int,
    val id: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun List<Review>.asMovieReviewsDatabaseModel(): List<LocalMovieReview> {
    return map {
        LocalMovieReview(
            videoId = it.videoId,
            id = it.id,
            username = it.username,
            avatarPath = it.avatarPath,
            content = it.content
        )
    }
}

fun List<Review>.asTVShowReviewsDatabaseModel(): List<LocalTVShowReview> {
    return map {
        LocalTVShowReview(
            videoId = it.videoId,
            id = it.id,
            username = it.username,
            avatarPath = it.avatarPath,
            content = it.content
        )
    }
}
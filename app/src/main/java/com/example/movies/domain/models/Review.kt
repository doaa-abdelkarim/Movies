package com.example.movies.domain.models

import com.example.movies.data.local.models.videos.movie.DatabaseMovieReview
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShowReview

data class Review(
    val videoId: Int? = null,
    val id: String? = null,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun List<Review>.asMovieReviewsDatabaseModel(): List<DatabaseMovieReview> {
    return map {
        DatabaseMovieReview(it.videoId, it.id?: "", it.username, it.avatarPath, it.content)
    }
}

fun List<Review>.asTVShowReviewsDatabaseModel(): List<DatabaseTVShowReview> {
    return map {
        DatabaseTVShowReview(it.videoId, it.id?: "", it.username, it.avatarPath, it.content)
    }
}
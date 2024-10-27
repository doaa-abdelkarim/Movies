package com.example.movies.data.local.models.videos.movies

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Review

@Entity(
    tableName = "movie_reviews_table",
    foreignKeys = [ForeignKey(
        entity = LocalMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalMovieReview(
    val videoId: Int,
    @PrimaryKey
    val reviewId: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun LocalMovieReview.asDomainModel(): Review =
    Review(
        videoId = videoId,
        reviewId = reviewId,
        username = username,
        avatarPath = avatarPath,
        content = content
    )
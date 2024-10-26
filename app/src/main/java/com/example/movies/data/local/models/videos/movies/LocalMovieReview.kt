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
    val id: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun LocalMovieReview.asDomainModel(): Review {
    return Review(
        videoId = videoId,
        id = id,
        username = username,
        avatarPath = avatarPath,
        content = content
    )
}

fun List<LocalMovieReview>.asDomainModel(): List<Review> =
    map {
        Review(
            videoId = it.videoId,
            id = it.id,
            username = it.username,
            avatarPath = it.avatarPath,
            content = it.content
        )
    }
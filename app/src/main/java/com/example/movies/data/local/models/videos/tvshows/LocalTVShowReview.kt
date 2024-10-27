package com.example.movies.data.local.models.videos.tvshows

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Review

@Entity(
    tableName = "tv_show_reviews_table",
    foreignKeys = [ForeignKey(
        entity = LocalTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalTVShowReview(
    val videoId: Int,
    @PrimaryKey
    val reviewId: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun LocalTVShowReview.asDomainModel(): Review {
    return Review(
        videoId = videoId,
        reviewId = reviewId,
        username = username,
        avatarPath = avatarPath,
        content = content
    )
}
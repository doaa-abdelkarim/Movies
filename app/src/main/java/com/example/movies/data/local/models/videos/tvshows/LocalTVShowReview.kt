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
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalTVShowReview(
    val videoId: Int,
    @PrimaryKey
    val id: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun LocalTVShowReview.asDomainModel(): Review {
    return Review(
        videoId = videoId,
        id = id,
        username = username,
        avatarPath = avatarPath,
        content = content
    )
}

fun List<LocalTVShowReview>.asDomainModel(): List<Review> =
    map {
        Review(
            videoId = it.videoId,
            id = it.id,
            username = it.username,
            avatarPath = it.avatarPath,
            content = it.content
        )
    }
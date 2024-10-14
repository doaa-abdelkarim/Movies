package com.example.movies.data.local.models.videos.tvshows

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Review

@Entity(
    tableName = "tv_show_review_table",
    foreignKeys = [ForeignKey(
        entity = LocalTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalTVShowReview(
    val videoId: Int? = null,
    @PrimaryKey
    val id: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun List<LocalTVShowReview>.asDomainModel() =
    map {
        Review(it.videoId, it.id, it.username, it.avatarPath, it.content)
    }
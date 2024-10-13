package com.example.movies.data.local.models.videos.tvshow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.models.Review

@Entity(
    tableName = "tv_show_review_table",
    foreignKeys = [ForeignKey(
        entity = DatabaseTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DatabaseTVShowReview(
    val videoId: Int? = null,
    @PrimaryKey
    val id: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun List<DatabaseTVShowReview>.asDomainModel() =
    map {
        Review(it.videoId, it.id, it.username, it.avatarPath, it.content)
    }
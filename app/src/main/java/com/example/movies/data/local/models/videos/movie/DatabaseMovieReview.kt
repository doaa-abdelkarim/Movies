package com.example.movies.data.local.models.videos.movie

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.models.Review

@Entity(
    tableName = "movie_review_table",
    foreignKeys = [ForeignKey(
        entity = DatabaseMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DatabaseMovieReview(
    val videoId: Int? = null,
    @PrimaryKey
    val id: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun List<DatabaseMovieReview>.asDomainModel() =
    map {
        Review(it.videoId, it.id, it.username, it.avatarPath, it.content)
    }
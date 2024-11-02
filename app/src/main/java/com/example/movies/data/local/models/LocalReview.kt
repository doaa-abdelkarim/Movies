package com.example.movies.data.local.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Review

@Entity(
    tableName = "reviews_table",
    foreignKeys = [ForeignKey(
        entity = LocalMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalReview(
    val movieId: Int,
    @PrimaryKey
    val reviewId: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)

fun LocalReview.asDomainModel(): Review =
    Review(
        movieId = movieId,
        reviewId = reviewId,
        username = username,
        avatarPath = avatarPath,
        content = content
    )
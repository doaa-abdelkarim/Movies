package com.example.movies.data.local.models.videos.movie

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.models.Clip

@Entity(
    tableName = "movie_clip_table",
    foreignKeys = [ForeignKey(
        entity = DatabaseMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DatabaseMovieClip(
    val videoId: Int? = null,
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val key: String? = null,
    val clipUri: String
)

fun List<DatabaseMovieClip>.asDomainModel() =
    map {
        Clip(it.videoId, it.id, it.name, it.key).apply { clipUri }
    }

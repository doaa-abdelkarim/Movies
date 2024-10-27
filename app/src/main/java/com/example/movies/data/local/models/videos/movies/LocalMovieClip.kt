package com.example.movies.data.local.models.videos.movies

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Clip

@Entity(
    tableName = "movie_clips_table",
    foreignKeys = [ForeignKey(
        entity = LocalMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalMovieClip(
    val videoId: Int,
    @PrimaryKey
    val clipId: String,
    val name: String? = null,
    val key: String? = null,
    val clipUri: String
)

fun List<LocalMovieClip>.asDomainModel() =
    map {
        Clip(it.videoId, it.clipId, it.name, it.key).apply { clipUri }
    }


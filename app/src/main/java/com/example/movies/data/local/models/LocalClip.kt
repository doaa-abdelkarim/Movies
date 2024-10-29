package com.example.movies.data.local.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Clip

@Entity(
    tableName = "clips_table",
    foreignKeys = [ForeignKey(
        entity = LocalMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalClip(
    val movieId: Int,
    @PrimaryKey
    val clipId: String,
    val name: String? = null,
    val key: String? = null,
    val clipUri: String? = null
)

fun List<LocalClip>.asDomainModel(): List<Clip> =
    map {
        Clip(
            videoId = it.movieId,
            clipId = it.clipId,
            name = it.name,
            key = it.key
        ).apply { clipUri }
    }


package com.example.movies.data.local.models.videos.tvshows

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Clip

@Entity(
    tableName = "tv_show_clips_table",
    foreignKeys = [ForeignKey(
        entity = LocalTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalTVShowClip(
    val videoId: Int,
    @PrimaryKey
    val clipId: String,
    val name: String? = null,
    val key: String? = null,
    val clipUri: String
)

fun List<LocalTVShowClip>.asDomainModel(): List<Clip> =
    map {
        Clip(
            videoId = it.videoId,
            clipId = it.clipId,
            name = it.name,
            key = it.key
        ).apply { clipUri }
    }
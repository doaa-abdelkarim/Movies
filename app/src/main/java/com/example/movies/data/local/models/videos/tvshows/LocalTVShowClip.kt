package com.example.movies.data.local.models.videos.tvshows

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Clip

@Entity(
    tableName = "tv_show_clip_table",
    foreignKeys = [ForeignKey(
        entity = LocalTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalTVShowClip(
    val videoId: Int? = null,
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val key: String? = null,
    val clipUri: String
)

fun List<LocalTVShowClip>.asDomainModel() =
    map {
        Clip(it.videoId, it.id, it.name, it.key).apply { clipUri }
    }
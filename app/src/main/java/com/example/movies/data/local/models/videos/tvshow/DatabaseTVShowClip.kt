package com.example.movies.data.local.models.videos.tvshow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.movies.domain.models.Clip

@Entity(
    tableName = "tv_show_clip_table",
    foreignKeys = [ForeignKey(
        entity = DatabaseTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class DatabaseTVShowClip(
    val videoId: Int? = null,
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val key: String? = null,
    val clipUri: String
)

fun List<DatabaseTVShowClip>.asDomainModel() =
    map {
        Clip(it.videoId, it.id, it.name, it.key).apply { clipUri }
    }
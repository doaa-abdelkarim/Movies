package com.example.movies.data.local.models.favorites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShow

@Entity(
    tableName = "favorite_tv_shows_table",
    foreignKeys = [ForeignKey(
        entity = DatabaseTVShow::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.NO_ACTION
    )],
    indices = [Index(value = ["videoId"], unique = true)]
)
data class DatabaseFavoriteTVShow(
    override val videoId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0
) : DatabaseFavorite()
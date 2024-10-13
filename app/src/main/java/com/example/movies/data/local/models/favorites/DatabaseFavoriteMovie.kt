package com.example.movies.data.local.models.favorites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.movie.DatabaseMovie

@Entity(
    tableName = "favorite_movies_table",
    foreignKeys = [ForeignKey(
        entity = DatabaseMovie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("videoId"),
        onDelete = ForeignKey.NO_ACTION
    )],
    indices = [Index(value = ["videoId"], unique = true)]
)
data class DatabaseFavoriteMovie(
    override val videoId: Int? = null,
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0
) : DatabaseFavorite()
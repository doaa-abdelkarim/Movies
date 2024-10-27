package com.example.movies.data.local.models.videos.movies

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.BaseLocalVideo

@Entity(
    tableName = "movies_table",
    indices = [Index(value = ["id"], unique = true)]
)
data class LocalMovie(
    @PrimaryKey(autoGenerate = true)
    override val pk: Int = 0,
    override val id: Int,
    override val posterPath: String? = null,
    override val backdropPath: String? = null,
    override val title: String? = null,
    override val popularity: Double? = null,
    override val genres: String? = null,
    override val originalLanguage: String? = null,
    override val overview: String? = null,
    override val releaseDate: String? = null,
    override val originalTitle: String? = null,
    val revenue: Int? = null
) : BaseLocalVideo()
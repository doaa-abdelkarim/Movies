package com.example.movies.data.local.models.videos.tvshows

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.BaseLocalVideo

@Entity(tableName = "tv_shows_table")
data class LocalTVShow(
    @PrimaryKey
    override val id: Int,
    override val posterPath: String? = null,
    override val backdropPath: String? = null,
    override val title: String? = null,
    override val popularity: Double? = null,
    override val genres: String? = null,
    override val originalLanguage: String? = null,
    override val overview: String? = null,
    override val releaseDate: String? = null,
    override val originalTitle: String? = null
) : BaseLocalVideo()
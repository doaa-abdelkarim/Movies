package com.example.movies.data.local.models.videos.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.BaseLocalVideo
import com.example.movies.domain.entities.Movie

@Entity(tableName = "movies_table")
data class LocalMovie(
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
    val revenue: Int? = null,
    override val originalTitle: String? = null,
    /*
    I added this field to fix "RemoteMediator calls API again and again" issue according to answer
    suggested in this link until I find better solution to this issue
    https://stackoverflow.com/a/76556967
     */
    val createdAt: Long? = System.currentTimeMillis()
) : BaseLocalVideo()
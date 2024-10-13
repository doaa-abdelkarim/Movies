package com.example.movies.data.local.models.videos.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.DatabaseVideo
import com.example.movies.domain.models.TVShow

@Entity(tableName = "tv_show_table")
data class DatabaseTVShow (
    @PrimaryKey
    override val id: Int? = null,
    override val posterPath: String? = null,
    override val backdropPath: String? = null,
    override val title: String? = null,
    override val popularity: Double? = null,
    override val genres: String? = null,
    override val originalLanguage: String? = null,
    override val overview: String? = null,
    override val releaseDate: String? = null,
    override val originalTitle: String? = null
): DatabaseVideo()

fun DatabaseTVShow.asDomainModel(): TVShow {
    return TVShow(id, posterPath, backdropPath, title, popularity, genres,
        originalLanguage, overview, releaseDate, originalTitle)
}

fun List<DatabaseTVShow>.asDomainModel() =
    map {
        TVShow(it.id, it.posterPath, it.backdropPath, it.title, it.popularity, it.genres,
            it.originalLanguage, it.overview, it.releaseDate, it.originalTitle)
    }
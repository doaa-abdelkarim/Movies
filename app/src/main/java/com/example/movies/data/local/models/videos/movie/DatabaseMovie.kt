package com.example.movies.data.local.models.videos.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.data.local.models.videos.DatabaseVideo
import com.example.movies.domain.models.Movie

@Entity(tableName = "movie_table")
data class DatabaseMovie (
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
    val revenue: Int? = null,
    override val originalTitle: String? = null
): DatabaseVideo()

fun DatabaseMovie.asDomainModel(): Movie {
    return Movie(id, posterPath, backdropPath, title, popularity, genres,
        originalLanguage, overview, releaseDate, this.revenue, originalTitle)
}

fun List<DatabaseMovie>.asDomainModel() =
    map {
        Movie(it.id, it.posterPath, it.backdropPath, it.title, it.popularity, it.genres,
            it.originalLanguage, it.overview, it.releaseDate, it.revenue, it.originalTitle
        )
    }
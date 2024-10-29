package com.example.movies.data.local.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movies.domain.entities.Movie

@Entity(
    tableName = "movies_table",
    indices = [Index(value = ["id"], unique = true)]
)
data class LocalMovie(
    /*
    I added this field to fix "RemoteMediator calls API again and again"
    A unique Int key which autoIncrements by 1 with every insert.
    I need this immutable key to maintain consistent item ordering so that the UI
    does not scroll out of order when new video are appended and inserted into this db.
     */
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: Int,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val title: String? = null,
    val popularity: Double? = null,
    val genres: String? = null,
    val originalLanguage: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val originalTitle: String? = null,
    val revenue: Int? = null,
    val isMovie: Boolean
)

fun LocalMovie.asDomainModel(): Movie =
    Movie(
        pk = pk,
        id = id,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = title,
        popularity = popularity,
        genres = genres,
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = releaseDate,
        originalTitle = originalTitle,
        revenue = revenue,
        isMovie = isMovie
    )
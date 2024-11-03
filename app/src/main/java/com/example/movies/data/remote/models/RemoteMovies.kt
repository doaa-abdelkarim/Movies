package com.example.movies.data.remote.models

import com.example.movies.data.local.models.LocalMovie
import com.example.movies.domain.entities.Movie
import com.google.gson.annotations.SerializedName

data class RemoteMovies(
    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<RemoteMovie?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

data class RemoteMovie(

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,

    @field:SerializedName("original_title")
    val originalTitle: String? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("origin_country")
    val originCountry: List<String?>? = null,

    @field:SerializedName("first_air_date")
    val firstAirDate: String? = null,

    @field:SerializedName("genres")
    val genres: List<RemoteGenresItem?>? = null,

    @field:SerializedName("revenue")
    val revenue: Int? = null,
)

fun RemoteMovies.asDatabaseModel(isMovie: Boolean): List<LocalMovie> =
    results
        ?.asSequence()
        ?.filterNotNull()
        ?.map {
            it.asDatabaseModel(isMovie= isMovie)
        }
        ?.toList() ?: emptyList()

fun RemoteMovie.asDomainModel(isMovie: Boolean): Movie =
    Movie(
        id = id!!,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = if (isMovie) title else name,
        popularity = popularity,
        genres = genres?.map { it?.name }?.joinToString(),
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = if (isMovie) releaseDate else firstAirDate,
        originalTitle = if (isMovie) originalTitle else originalName,
        revenue = revenue,
        isMovie = isMovie
    )

fun RemoteMovie.asDatabaseModel(isMovie: Boolean): LocalMovie =
    LocalMovie(
        id = id!!,
        posterPath = posterPath,
        backdropPath = backdropPath,
        title = if (isMovie) title else name,
        popularity = popularity,
        genres = genres?.map { it?.name }?.joinToString(),
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = if (isMovie) releaseDate else firstAirDate,
        originalTitle = if (isMovie) originalTitle else originalName,
        revenue = revenue,
        isMovie = isMovie
    )




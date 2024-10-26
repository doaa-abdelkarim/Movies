package com.example.movies.domain.entities

import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
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
    override val originalTitle: String? = null
) : BaseVideo()
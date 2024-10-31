package com.example.movies.domain.entities

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.example.movies.util.constants.AppConstants.Companion.IMAGE_BASE_URL
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class Movie(
    val pk: Int? = null,
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
) : Parcelable {
    val posterUri: Uri
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(posterPath)
            .build()

    val backdropUri: Uri
        get() = Uri.parse(IMAGE_BASE_URL)
            .buildUpon()
            .appendPath(backdropPath)
            .build()
}

val MovieNavType = object : NavType<Movie>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Movie {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Movie::class.java) as Movie
        } else {
            bundle.getParcelable<Movie>(key) as Movie
        }
    }

    override fun parseValue(value: String): Movie {
        return Json.decodeFromString<Movie>(value)
    }

    override fun put(bundle: Bundle, key: String, value: Movie) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: Movie): String {
        return Json.encodeToString(value)
    }
}
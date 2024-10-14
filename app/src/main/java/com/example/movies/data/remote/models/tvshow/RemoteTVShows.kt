package com.example.movies.data.remote.models.tvshow

import com.example.movies.domain.entities.TVShow
import com.example.movies.domain.entities.Video
import com.google.gson.annotations.SerializedName

data class RemoteTVShows(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<NetworkTVShows?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class NetworkTVShows(

	@field:SerializedName("first_air_date")
	val firstAirDate: String? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("origin_country")
	val originCountry: List<String?>? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("original_name")
	val originalName: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)

fun RemoteTVShows.asDomainModel(): List<Video> {
	return results?.asSequence()
		?.filterNotNull()
		?.map {
			TVShow(it.id, it.posterPath, it.backdropPath, it.name, it.popularity)
		}?.toList()
		?: listOf()
}

/*
fun TVShowsResponse.asDatabaseModel(): List<DatabaseVideo> {
	return results?.asSequence()
		?.filterNotNull()
		?.map {
			DatabaseTVShow(id = it.id)
		}?.toList()
		?: listOf()
}

 */


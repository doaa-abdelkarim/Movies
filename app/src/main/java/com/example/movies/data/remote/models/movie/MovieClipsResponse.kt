package com.example.movies.data.remote.models.movie

import com.example.movies.data.remote.models.VideosResultsItem
import com.example.movies.domain.models.Clip
import com.google.gson.annotations.SerializedName

data class MovieClipsResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideosResultsItem?>? = null
)

fun MovieClipsResponse.asDomainModel() =
	results?.asSequence()
		?.filterNotNull()
		?.map {
			Clip(this.id, it.id, it.name, it.key)
		}?.toList()
		?: listOf()



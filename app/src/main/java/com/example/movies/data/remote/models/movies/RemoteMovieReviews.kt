package com.example.movies.data.remote.models.movies

import com.example.movies.data.remote.models.ReviewsResultsItem
import com.example.movies.domain.entities.Review
import com.google.gson.annotations.SerializedName

data class RemoteMovieReviews(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<ReviewsResultsItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)

fun RemoteMovieReviews.asDomainModel() =
    results?.asSequence()
        ?.filterNotNull()
        ?.map {
            Review(this.id, it.id, it.authorDetails?.username,
                it.authorDetails?.avatarPath, it.content
            )
        }?.toList()
        ?: listOf()




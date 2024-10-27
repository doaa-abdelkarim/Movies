package com.example.movies.data.remote.models

import com.example.movies.data.local.models.videos.movies.LocalMovieReview
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview
import com.example.movies.domain.entities.Review
import com.google.gson.annotations.SerializedName

data class RemoteReviews(

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

data class ReviewsResultsItem(

    @field:SerializedName("author_details")
    val authorDetails: AuthorDetails? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class AuthorDetails(

    @field:SerializedName("avatar_path")
    val avatarPath: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Double? = null,

    @field:SerializedName("username")
    val username: String? = null
)

fun ReviewsResultsItem.asDomainModel(videoId: Int): Review =
    Review(
        videoId = videoId,
        id = id!!,
        username = authorDetails?.username,
        avatarPath = authorDetails?.avatarPath,
        content = content
    )

fun RemoteReviews.asMovieReviewsDatabaseModel(): List<LocalMovieReview> {
    return results
        ?.asSequence()
        ?.filterNotNull()
        ?.map {
            LocalMovieReview(
                videoId = this.id!!,
                reviewId = it.id!!,
                username = it.authorDetails?.username,
                avatarPath = it.authorDetails?.avatarPath,
                content = it.content,
            )
        }
        ?.toList() ?: emptyList()
}

fun RemoteReviews.asTVShowReviewsDatabaseModel(): List<LocalTVShowReview> {
    return results
        ?.asSequence()
        ?.filterNotNull()
        ?.map {
            LocalTVShowReview(
                videoId = this.id!!,
                reviewId = it.id!!,
                username = it.authorDetails?.username,
                avatarPath = it.authorDetails?.avatarPath,
                content = it.content,
            )
        }
        ?.toList() ?: emptyList()
}






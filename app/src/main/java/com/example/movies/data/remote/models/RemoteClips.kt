package com.example.movies.data.remote.models

import com.example.movies.data.local.models.LocalClip
import com.example.movies.domain.entities.Clip
import com.example.movies.util.getYouTubeUriByKey
import com.google.gson.annotations.SerializedName

data class RemoteClips(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("results")
    val results: List<ClipsResultsItem?>? = null
)

data class ClipsResultsItem(

    @field:SerializedName("site")
    val site: String? = null,

    @field:SerializedName("size")
    val size: Int? = null,

    @field:SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("official")
    val official: Boolean? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("published_at")
    val publishedAt: String? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null,

    @field:SerializedName("key")
    val key: String? = null
)

fun RemoteClips.asDomainModel() =
    results
        ?.asSequence()
        ?.filterNotNull()
        ?.map {
            Clip(
                videoId = this.id!!,
                clipId = it.id!!,
                name = it.name,
                key = it.key
            )
        }?.toList() ?: listOf()

fun RemoteClips.asDatabaseModel(): List<LocalClip> =
    results
        ?.asSequence()
        ?.filterNotNull()
        ?.map {
            LocalClip(
                movieId = this.id!!,
                clipId = it.id!!,
                name = it.name,
                key = it.key,
                clipUri = it.key?.let { getYouTubeUriByKey(it) }?.toString()
            )
        }?.toList() ?: listOf()



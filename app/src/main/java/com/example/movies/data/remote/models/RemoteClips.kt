package com.example.movies.data.remote.models

import com.example.movies.data.local.models.LocalClip
import com.example.movies.domain.entities.Clip
import com.example.movies.util.getYouTubeUriByKey
import com.google.gson.annotations.SerializedName

data class RemoteClips(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("results")
    val results: List<RemoteClip?>? = null
)

data class RemoteClip(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("key")
    val key: String? = null
)

fun RemoteClips.asDomainModel() =
    results
        ?.asSequence()
        ?.filterNotNull()
        ?.map {
            Clip(
                movieId = this.id!!,
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
                clipUri = it.key?.let { key -> getYouTubeUriByKey(key) }?.toString()
            )
        }?.toList() ?: listOf()



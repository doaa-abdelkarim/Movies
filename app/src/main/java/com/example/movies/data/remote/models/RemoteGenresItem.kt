package com.example.movies.data.remote.models

import com.google.gson.annotations.SerializedName

data class RemoteGenresItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
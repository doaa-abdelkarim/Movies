package com.example.movies.data.remote.models

import com.google.gson.annotations.SerializedName

data class RemoteProductionCountriesItem(

    @field:SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)
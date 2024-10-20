package com.example.movies.data.local.models.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_remote_keys_table"
)
data class MovieRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
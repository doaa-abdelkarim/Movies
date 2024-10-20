package com.example.movies.data.local.models.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_shows_remote_keys_table"
)
data class TVShowsRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
package com.example.movies.data.local.models.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_show_remote_keys_table"
)
data class TVShowRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
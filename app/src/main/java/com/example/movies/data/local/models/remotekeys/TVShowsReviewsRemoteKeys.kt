package com.example.movies.data.local.models.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_show_reviews_remote_keys_table"
)
data class TVShowReviewsRemoteKeys(
    @PrimaryKey
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
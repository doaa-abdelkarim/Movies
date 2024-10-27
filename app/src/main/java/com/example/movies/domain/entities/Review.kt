package com.example.movies.domain.entities

data class Review(
    val videoId: Int,
    val reviewId: String,
    val username: String? = null,
    val avatarPath: String? = null,
    val content: String? = null
)
package com.example.movies.domain.repositories

import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video

interface BaseVideosRepository {
    suspend fun getVideos(page: Int): List<Video>
    suspend fun getVideoInfo(videoId: Int): Video
    suspend fun getVideoClips(videoId: Int): List<Clip>
    suspend fun getVideoReviews(videoId: Int, page: Int): List<Review>
    suspend fun cacheVideos(videos: List<Video>)
    suspend fun cacheVideoDetails(video: Video)
    suspend fun cacheVideoClips(clips: List<Clip>)
    suspend fun cacheVideoReviews(reviews: List<Review>)
}
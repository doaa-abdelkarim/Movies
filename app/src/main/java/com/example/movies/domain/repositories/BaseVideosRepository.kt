package com.example.movies.domain.repositories

import androidx.paging.PagingData
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import kotlinx.coroutines.flow.Flow

interface BaseVideosRepository {
    fun getVideos(): Flow<PagingData<Video>>
    suspend fun getVideoInfo(videoId: Int): Video
    suspend fun getVideoClips(videoId: Int): List<Clip>
    fun getVideoReviews(videoId: Int): Flow<PagingData<Review>>
    suspend fun cacheVideos(videos: List<Video>)
    suspend fun cacheVideoDetails(video: Video)
    suspend fun cacheVideoClips(clips: List<Clip>)
    suspend fun cacheVideoReviews(reviews: List<Review>)
}
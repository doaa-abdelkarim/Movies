package com.example.movies.domain.repositories

import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video

interface BaseVideosRepositoryRepository {
    suspend fun getVideos(page: Int): List<Video>
    suspend fun getVideoDetails(videoId: Int): Video
    suspend fun getVideoClips(videoId: Int): List<Clip>
    suspend fun getVideoReviews(videoId: Int, page: Int): List<Review>
}

interface BaseOfflineVideosRepository {
    suspend fun cacheVideos(videos: List<Video>)
    suspend fun cacheVideoDetails(video: Video)
    suspend fun cacheVideoClips(clips: List<Clip>)
    suspend fun cacheVideoReviews(reviews: List<Review>)
}

interface BaseMoviesRepositoryRepository : BaseVideosRepositoryRepository
interface BaseOnlineMoviesRepository : BaseMoviesRepositoryRepository
interface BaseOfflineMoviesRepository : BaseMoviesRepositoryRepository, BaseOfflineVideosRepository

interface BaseTVShowsRepositoryRepository : BaseVideosRepositoryRepository
interface BaseOnlineTVShowsRepository : BaseTVShowsRepositoryRepository
interface BaseOfflineTVShowsRepository : BaseTVShowsRepositoryRepository, BaseOfflineVideosRepository
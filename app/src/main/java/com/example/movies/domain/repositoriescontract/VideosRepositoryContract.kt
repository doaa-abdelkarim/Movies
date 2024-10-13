package com.example.movies.domain.repositoriescontract

import com.example.movies.domain.models.Clip
import com.example.movies.domain.models.Review
import com.example.movies.domain.models.Video

interface VideosRepositoryContract {
    suspend fun getVideos(page: Int): List<Video>
    suspend fun getVideoDetails(videoId: Int): Video
    suspend fun getVideoClips(videoId: Int): List<Clip>
    suspend fun getVideoReviews(videoId: Int, page: Int): List<Review>
}

interface OfflineVideos {
    suspend fun cacheVideos(videos: List<Video>)
    suspend fun cacheVideoDetails(video: Video)
    suspend fun cacheVideoClips(clips: List<Clip>)
    suspend fun cacheVideoReviews(reviews: List<Review>)
}

interface MoviesRepositoryContract : VideosRepositoryContract
interface OnlineMovies : MoviesRepositoryContract
interface OfflineMovies : MoviesRepositoryContract, OfflineVideos

interface TVShowsRepositoryContract : VideosRepositoryContract
interface OnlineTVShows : TVShowsRepositoryContract
interface OfflineTVShows : TVShowsRepositoryContract, OfflineVideos
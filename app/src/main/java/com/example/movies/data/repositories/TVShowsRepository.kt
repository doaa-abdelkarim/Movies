package com.example.movies.data.repositories

import com.example.movies.data.local.datasources.BaseTVShowsLocalDataSource
import com.example.movies.data.remote.datasources.BaseTVShowsRemoteDataSource
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.NetworkHandler

class TVShowsRepository(
    private val baseTVShowsRemoteDataSource: BaseTVShowsRemoteDataSource,
    private val baseTVShowsLocalDataSource: BaseTVShowsLocalDataSource,
    private val networkHandler: NetworkHandler
) : BaseVideosRepository {
    override suspend fun getVideos(page: Int): List<Video> {
        if (networkHandler.isOnline()) {
            val tvShows = baseTVShowsRemoteDataSource.getVideos(page)
            baseTVShowsLocalDataSource.cacheVideos(tvShows)
            return tvShows
        }
        return baseTVShowsLocalDataSource.getVideos(page)
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val tvShow = baseTVShowsRemoteDataSource.getVideoInfo(videoId)
            baseTVShowsLocalDataSource.cacheVideoDetails(tvShow)
            return tvShow
        }
        return baseTVShowsLocalDataSource.getVideoInfo(videoId)
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = baseTVShowsRemoteDataSource.getVideoClips(videoId)
            baseTVShowsLocalDataSource.cacheVideoClips(clips)
            return clips
        }
        return baseTVShowsLocalDataSource.getVideoClips(videoId)
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        if (networkHandler.isOnline()) {
            val reviews = baseTVShowsRemoteDataSource.getVideoReviews(videoId, page)
            baseTVShowsLocalDataSource.cacheVideoReviews(reviews)
            return reviews
        }
        return baseTVShowsLocalDataSource.getVideoReviews(videoId, page)
    }

    override suspend fun cacheVideos(videos: List<Video>) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheVideoDetails(video: Video) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheVideoClips(clips: List<Clip>) {
        TODO("Not yet implemented")
    }

    override suspend fun cacheVideoReviews(reviews: List<Review>) {
        TODO("Not yet implemented")
    }

}
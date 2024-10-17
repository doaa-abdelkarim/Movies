package com.example.movies.data.repositories

import androidx.paging.PagingData
import com.example.movies.data.local.datasources.BaseTVShowsLocalDataSource
import com.example.movies.data.remote.datasources.BaseTVShowsRemoteDataSource
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.NetworkHandler
import kotlinx.coroutines.flow.Flow

class TVShowsRepository(
    private val baseTVShowsRemoteDataSource: BaseTVShowsRemoteDataSource,
    private val baseTVShowsLocalDataSource: BaseTVShowsLocalDataSource,
    private val networkHandler: NetworkHandler
) : BaseVideosRepository {
    override fun getVideos(): Flow<PagingData<Video>> {
        if (networkHandler.isOnline()) {
            val tvShows = baseTVShowsRemoteDataSource.getVideos()
//            baseTVShowsLocalDataSource.cacheVideos(tvShows)
            return tvShows
        }
        return baseTVShowsLocalDataSource.getVideos()
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

    override fun getVideoReviews(videoId: Int): Flow<PagingData<Review>> {
        if (networkHandler.isOnline()) {
            val reviews = baseTVShowsRemoteDataSource.getVideoReviews(videoId)
//            baseTVShowsLocalDataSource.cacheVideoReviews(reviews)
            return reviews
        }
        return baseTVShowsLocalDataSource.getVideoReviews(videoId)
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
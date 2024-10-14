package com.example.movies.data.repositories

import com.example.movies.data.local.datasources.BaseMoviesLocalDataSource
import com.example.movies.data.remote.datasources.BaseMoviesRemoteDataSource
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.NetworkHandler

class MoviesRepository(
    private val baseMoviesRemoteDataSource: BaseMoviesRemoteDataSource,
    private val baseMoviesLocalDataSource: BaseMoviesLocalDataSource,
    private val networkHandler: NetworkHandler
) : BaseVideosRepository {
    override suspend fun getVideos(page: Int): List<Video> {
        if (networkHandler.isOnline()) {
            val movies = baseMoviesRemoteDataSource.getVideos(page)
            baseMoviesLocalDataSource.cacheVideos(movies)
            return movies
        }
        return baseMoviesLocalDataSource.getVideos(page)
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val movie = baseMoviesRemoteDataSource.getVideoDetails(videoId)
            baseMoviesLocalDataSource.cacheVideoDetails(movie)
            return movie
        }
        return baseMoviesLocalDataSource.getVideoDetails(videoId)
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = baseMoviesRemoteDataSource.getVideoClips(videoId)
            baseMoviesLocalDataSource.cacheVideoClips(clips)
            return clips
        }
        return baseMoviesLocalDataSource.getVideoClips(videoId)
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        if (networkHandler.isOnline()) {
            val reviews = baseMoviesRemoteDataSource.getVideoReviews(videoId, page)
            baseMoviesLocalDataSource.cacheVideoReviews(reviews)
            return reviews
        }
        return baseMoviesLocalDataSource.getVideoReviews(videoId, page)
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


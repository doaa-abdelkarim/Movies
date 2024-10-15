package com.example.movies.data.remote.datasources

import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDomainModel
import com.example.movies.data.remote.models.asMovieDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository

interface BaseMoviesRemoteDataSource : BaseVideosRepository

class MoviesRemoteDataSource(
    private val moviesAPI: MoviesAPI
) : BaseMoviesRemoteDataSource {
    override suspend fun getVideos(page: Int): List<Video> {
        return moviesAPI.getMovies(page).asMovieDomainModel()
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        return moviesAPI.getMovieInfo(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return moviesAPI.getMovieClips(videoId).asDomainModel()
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        return moviesAPI.getMovieReviews(videoId, page).asDomainModel()
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
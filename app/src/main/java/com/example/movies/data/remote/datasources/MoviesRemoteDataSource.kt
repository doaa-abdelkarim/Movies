package com.example.movies.data.remote.datasources

import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.movies.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository

interface BaseMoviesRemoteDataSource : BaseVideosRepository

class MoviesRemoteDataSource(private val moviesAPI: MoviesAPI) : BaseMoviesRemoteDataSource {
    override suspend fun getVideos(page: Int): List<Video> {
        return moviesAPI.getMovies(page).asDomainModel()
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        return moviesAPI.getMovieDetails(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return moviesAPI.getMovieClips(videoId).asDomainModel()
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        return moviesAPI.getMovieReviews(videoId, page).asDomainModel()
    }

}
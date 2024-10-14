package com.example.movies.data.local.datasources

import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MovieReviewsDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.data.local.models.videos.movies.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.entities.asMovieClipsDatabaseModel
import com.example.movies.domain.entities.asMovieDatabaseModel
import com.example.movies.domain.entities.asMovieReviewsDatabaseModel
import com.example.movies.domain.repositories.BaseVideosRepository

interface BaseMoviesLocalDataSource : BaseVideosRepository

class MoviesLocalDataSource(
    private val moviesDao: MoviesDao,
    private val movieClipsDao: MovieClipsDao,
    private val movieReviewsDao: MovieReviewsDao
) : BaseMoviesLocalDataSource {

    override suspend fun getVideos(page: Int): List<Video> {
        return moviesDao.getAllMovies().asDomainModel()
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        return moviesDao.getMovieById(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return movieClipsDao.getAllClips(videoId).asDomainModel()
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        return movieReviewsDao.getAllReviews(videoId).asDomainModel()
    }

    override suspend fun cacheVideos(videos: List<Video>) {
        moviesDao.insert(videos.asMovieDatabaseModel())
    }

    override suspend fun cacheVideoDetails(video: Video) {
        moviesDao.update(video.asMovieDatabaseModel())
    }

    override suspend fun cacheVideoClips(clips: List<Clip>) {
        movieClipsDao.insert(clips.asMovieClipsDatabaseModel())
    }

    override suspend fun cacheVideoReviews(reviews: List<Review>) {
        movieReviewsDao.insert(reviews.asMovieReviewsDatabaseModel())
    }

}
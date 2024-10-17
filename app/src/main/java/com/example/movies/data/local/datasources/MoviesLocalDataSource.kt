package com.example.movies.data.local.datasources

import androidx.paging.PagingData
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
import kotlinx.coroutines.flow.Flow

interface BaseMoviesLocalDataSource : BaseVideosRepository

class MoviesLocalDataSource(
    private val moviesDao: MoviesDao,
    private val movieClipsDao: MovieClipsDao,
    private val movieReviewsDao: MovieReviewsDao
) : BaseMoviesLocalDataSource {

    override fun getVideos(): Flow<PagingData<Video>> {
//        return moviesDao.getAllMovies().asDomainModel()
        TODO("Not yet implemented")
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        return moviesDao.getMovieById(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return movieClipsDao.getAllClips(videoId).asDomainModel()
    }

    override fun getVideoReviews(videoId: Int): Flow<PagingData<Review>> {
//        return movieReviewsDao.getAllReviews(videoId).asDomainModel()
        TODO("Not yet implemented")
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
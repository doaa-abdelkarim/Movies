package com.example.movies.domain.repositories

import com.example.movies.data.local.models.videos.movies.asDomainModel
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.movies.asDomainModel
import com.example.movies.data.local.db.dao.MovieClipsDao
import com.example.movies.data.local.db.dao.MovieReviewsDao
import com.example.movies.data.local.db.dao.MoviesDao
import com.example.movies.domain.entities.*
import com.example.movies.util.NetworkHandler

class MoviesRepositoryRepository(
    private val baseOnlineMoviesRepository: BaseOnlineMoviesRepository,
    private val baseOfflineMoviesRepository: BaseOfflineMoviesRepository,
    private val networkHandler: NetworkHandler
) : BaseMoviesRepositoryRepository {
    override suspend fun getVideos(page: Int): List<Video> {
        if (networkHandler.isOnline()) {
            val movies = baseOnlineMoviesRepository.getVideos(page)
            baseOfflineMoviesRepository.cacheVideos(movies)
            return movies
        }
        return baseOfflineMoviesRepository.getVideos(page)
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val movie = baseOnlineMoviesRepository.getVideoDetails(videoId)
            baseOfflineMoviesRepository.cacheVideoDetails(movie)
            return movie
        }
        return baseOfflineMoviesRepository.getVideoDetails(videoId)
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = baseOnlineMoviesRepository.getVideoClips(videoId)
            baseOfflineMoviesRepository.cacheVideoClips(clips)
            return clips
        }
        return baseOfflineMoviesRepository.getVideoClips(videoId)
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        if (networkHandler.isOnline()) {
            val reviews = baseOnlineMoviesRepository.getVideoReviews(videoId, page)
            baseOfflineMoviesRepository.cacheVideoReviews(reviews)
            return reviews
        }
        return baseOfflineMoviesRepository.getVideoReviews(videoId, page)
    }

}

class OnlineMoviesRepository(private val moviesAPI: MoviesAPI) : BaseOnlineMoviesRepository {
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

class OfflineMoviesRepository(
    private val moviesDao: MoviesDao,
    private val movieClipsDao: MovieClipsDao,
    private val movieReviewsDao: MovieReviewsDao
) : BaseOfflineMoviesRepository {
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

}
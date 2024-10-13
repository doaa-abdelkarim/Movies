package com.example.movies.domain.repositoriescontract

import com.example.movies.data.local.models.videos.movie.asDomainModel
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.movie.asDomainModel
import com.example.movies.db.dao.MovieClipsDao
import com.example.movies.db.dao.MovieReviewsDao
import com.example.movies.db.dao.MoviesDao
import com.example.movies.domain.models.*
import com.example.movies.util.NetworkHandler

class MoviesRepositoryImp(
    private val onlineMovies: OnlineMovies,
    private val offlineMovies: OfflineMovies,
    private val networkHandler: NetworkHandler
) : MoviesRepositoryContract {
    override suspend fun getVideos(page: Int): List<Video> {
        if (networkHandler.isOnline()) {
            val movies = onlineMovies.getVideos(page)
            offlineMovies.cacheVideos(movies)
            return movies
        }
        return offlineMovies.getVideos(page)
    }

    override suspend fun getVideoDetails(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val movie = onlineMovies.getVideoDetails(videoId)
            offlineMovies.cacheVideoDetails(movie)
            return movie
        }
        return offlineMovies.getVideoDetails(videoId)
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = onlineMovies.getVideoClips(videoId)
            offlineMovies.cacheVideoClips(clips)
            return clips
        }
        return offlineMovies.getVideoClips(videoId)
    }

    override suspend fun getVideoReviews(videoId: Int, page: Int): List<Review> {
        if (networkHandler.isOnline()) {
            val reviews = onlineMovies.getVideoReviews(videoId, page)
            offlineMovies.cacheVideoReviews(reviews)
            return reviews
        }
        return offlineMovies.getVideoReviews(videoId, page)
    }

}

class OnlineMoviesImpl(private val moviesAPI: MoviesAPI) : OnlineMovies {
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

class OfflineMoviesImpl(
    private val moviesDao: MoviesDao,
    private val movieClipsDao: MovieClipsDao,
    private val movieReviewsDao: MovieReviewsDao
) : OfflineMovies {
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
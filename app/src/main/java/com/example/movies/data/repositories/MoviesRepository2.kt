package com.example.movies.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.models.videos.asDomainModel
import com.example.movies.data.local.models.videos.movies.LocalMovie
import com.example.movies.data.local.models.videos.movies.asDomainModel
import com.example.movies.data.paging.MovieReviewsRemoteMediator
import com.example.movies.data.paging.MoviesRemoteMediator
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.entities.asMovieClipsDatabaseModel
import com.example.movies.domain.entities.asDatabaseModel
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.NetworkHandler
import com.example.movies.util.getDefaultPageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Room is the single source of truth
@OptIn(ExperimentalPagingApi::class)
class MoviesRepository2(
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
    private val networkHandler: NetworkHandler
) : BaseVideosRepository {
    override fun getVideos(): Flow<PagingData<Video>> {
        val pagingSourceFactory = { moviesDB.moviesDao().getAllMovies() }
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = MoviesRemoteMediator(
                moviesAPI = moviesAPI,
                moviesDB = moviesDB,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { video -> video.asDomainModel() }
        }
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        val moviesDao = moviesDB.moviesDao()
        if (networkHandler.isOnline()) {
            val movie = moviesAPI.getMovieInfo(videoId).asDomainModel()
            moviesDao.update(movie.asDatabaseModel() as LocalMovie)
        }
        return moviesDao.getMovieById(videoId).asDomainModel()

    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        val movieClipsDao = moviesDB.movieClipsDao()
        if (networkHandler.isOnline()) {
            val clips = moviesAPI.getMovieClips(videoId).asDomainModel()
            movieClipsDao.insert(clips.asMovieClipsDatabaseModel())
        }
        return movieClipsDao.getClips(videoId).asDomainModel()
    }

    override fun getVideoReviews(videoId: Int): Flow<PagingData<Review>> {
        val pagingSourceFactory = { moviesDB.movieReviewsDao().getReviews(movieId = videoId) }
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = MovieReviewsRemoteMediator(
                moviesAPI = moviesAPI,
                moviesDB = moviesDB,
                movieId = videoId
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { review -> review.asDomainModel() }
        }
    }
}

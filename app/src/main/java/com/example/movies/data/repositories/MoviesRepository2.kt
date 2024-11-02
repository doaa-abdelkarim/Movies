package com.example.movies.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.models.asDomainModel
import com.example.movies.data.paging.MovieReviewsRemoteMediator
import com.example.movies.data.paging.MoviesRemoteMediator
import com.example.movies.data.paging.TVShowReviewsRemoteMediator
import com.example.movies.data.paging.TVShowsRemoteMediator
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDatabaseModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.helpers.NetworkHandler
import com.example.movies.util.getDefaultPageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Room is the single source of truth
@OptIn(ExperimentalPagingApi::class)
class MoviesRepository2(
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
    private val networkHandler: NetworkHandler
) : BaseMoviesRepository {
    override fun getMovies(): Flow<PagingData<Movie>> {
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

    override fun getTVShows(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { moviesDB.moviesDao().getAllTVShows() }
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = TVShowsRemoteMediator(
                moviesAPI = moviesAPI,
                moviesDB = moviesDB,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { video -> video.asDomainModel() }
        }
    }

    override suspend fun getMovieInfo(id: Int): Movie {
        val moviesDao = moviesDB.moviesDao()
        if (networkHandler.isOnline()) {
            val movie = moviesAPI.getMovieInfo(id)
            val pk = moviesDao.getMovieById(id).pk
            moviesDao.update((movie.asDatabaseModel()).copy(pk = pk))
        }
        return moviesDao.getMovieById(id).asDomainModel()
    }

    override suspend fun getTVShowInfo(id: Int): Movie {
        val moviesDao = moviesDB.moviesDao()
        if (networkHandler.isOnline()) {
            val movie = moviesAPI.getTVShowInfo(id)
            val pk = moviesDao.getMovieById(id).pk
            moviesDao.update((movie.asDatabaseModel()).copy(pk = pk))
        }
        return moviesDao.getMovieById(id).asDomainModel()
    }

    override suspend fun getMovieClips(id: Int): List<Clip> {
        val movieClipsDao = moviesDB.clipsDao()
        if (networkHandler.isOnline()) {
            val clips = moviesAPI.getMovieClips(id)
            movieClipsDao.insert(clips.asDatabaseModel())
        }
        return movieClipsDao.getClips(id = id).asDomainModel()
    }

    override suspend fun getTVShowClips(id: Int): List<Clip> {
        val movieClipsDao = moviesDB.clipsDao()
        if (networkHandler.isOnline()) {
            val clips = moviesAPI.getTVShowClips(id)
            movieClipsDao.insert(clips.asDatabaseModel())
        }
        return movieClipsDao.getClips(id = id).asDomainModel()
    }

    override fun getMovieReviews(id: Int): Flow<PagingData<Review>> {
        val pagingSourceFactory = { moviesDB.reviewsDao().getReviews(id = id) }
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = MovieReviewsRemoteMediator(
                moviesAPI = moviesAPI,
                moviesDB = moviesDB,
                id = id
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { review -> review.asDomainModel() }
        }
    }

    override fun getTVShowReviews(id: Int): Flow<PagingData<Review>> {
        val pagingSourceFactory = { moviesDB.reviewsDao().getReviews(id = id) }
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = TVShowReviewsRemoteMediator(
                moviesAPI = moviesAPI,
                moviesDB = moviesDB,
                id = id
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map {
            it.map { review -> review.asDomainModel() }
        }
    }
}

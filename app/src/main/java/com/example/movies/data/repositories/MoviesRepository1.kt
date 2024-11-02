package com.example.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.paging.ReviewPagingSource
import com.example.movies.data.paging.VideosPagingSource
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.constants.enums.VideoType
import com.example.movies.util.constants.enums.VideoType.MOVIE
import com.example.movies.util.constants.enums.VideoType.TV_SHOW
import com.example.movies.util.getDefaultPageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Use it if network is the single source of truth
class MoviesRepository1(
    private val moviesAPI: MoviesAPI
) : BaseMoviesRepository {
    override fun getMovies(): Flow<PagingData<Movie>> = getVideos(isMovie = true)

    override fun getTVShows(): Flow<PagingData<Movie>> = getVideos(isMovie = false)

    private fun getVideos(isMovie: Boolean): Flow<PagingData<Movie>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = {
                VideosPagingSource(
                    moviesAPI = moviesAPI,
                    videoType = MOVIE
                )
            }
        ).flow.map {
            it.map { video -> video.asDomainModel(isMovie = isMovie) }
        }
    }

    override suspend fun getMovieInfo(id: Int): Movie {
        return moviesAPI.getMovieInfo(id).asDomainModel()
    }

    override suspend fun getTVShowInfo(id: Int): Movie {
        return moviesAPI.getTVShowInfo(id).asDomainModel()    }

    override suspend fun getMovieClips(id: Int): List<Clip> {
        return moviesAPI.getMovieClips(id).asDomainModel()
    }

    override suspend fun getTVShowClips(id: Int): List<Clip> {
        return moviesAPI.getTVShowClips(id).asDomainModel()
    }

    override fun getMovieReviews(id: Int): Flow<PagingData<Review>> =
        getReviews(id = id, videoType = MOVIE)

    override fun getTVShowReviews(id: Int): Flow<PagingData<Review>> =
        getReviews(id = id, videoType = TV_SHOW)

    private fun getReviews(id: Int, videoType: VideoType): Flow<PagingData<Review>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = {
                ReviewPagingSource(
                    moviesAPI = moviesAPI,
                    videoType = videoType,
                    videoId = id
                )
            }
        ).flow.map {
            it.map { review -> review.asDomainModel(videoId = id) }
        }
    }

}


package com.example.movies.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.paging.ReviewPagingSource
import com.example.movies.data.paging.VideosPagingSource
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDomainModel
import com.example.movies.data.remote.models.asMovieDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.VideoType.MOVIE
import com.example.movies.util.getDefaultPageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Use it if network is the single source of truth
class MoviesRepository1(
    private val moviesAPI: MoviesAPI
) : BaseVideosRepository {
    override fun getVideos(): Flow<PagingData<Video>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = {
                VideosPagingSource(
                    moviesAPI = moviesAPI,
                    videoType = MOVIE
                )
            }
        ).flow.map {
            it.map { video -> video.asMovieDomainModel() }
        }
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        return moviesAPI.getMovieInfo(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        return moviesAPI.getMovieClips(videoId).asDomainModel()
    }

    override fun getVideoReviews(videoId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = {
                ReviewPagingSource(
                    moviesAPI = moviesAPI,
                    videoType = MOVIE,
                    videoId = videoId
                )
            }
        ).flow.map {
            it.map { review -> review.asDomainModel(videoId = videoId) }
        }
    }

}


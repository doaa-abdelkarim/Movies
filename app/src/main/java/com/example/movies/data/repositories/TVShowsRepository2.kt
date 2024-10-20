package com.example.movies.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TVShowsRemoteKeysDao
import com.example.movies.data.local.db.dao.TvShowClipsDao
import com.example.movies.data.local.models.videos.tvshows.asDomainModel
import com.example.movies.data.paging.TVShowsRemoteMediator
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDomainModel
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.entities.asTVShowClipsDatabaseModel
import com.example.movies.domain.entities.asTVShowDatabaseModel
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.NetworkHandler
import com.example.movies.util.getDefaultPageConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Room is the single source of truth
@OptIn(ExperimentalPagingApi::class)
class TVShowsRepository2(
    private val networkHandler: NetworkHandler,
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
    private val tvShowsRemoteKeysDao: TVShowsRemoteKeysDao,
    private val tvShowsDao: TVShowsDao,
    private val tvShowClipsDao: TvShowClipsDao,
) : BaseVideosRepository {
    override fun getVideos(): Flow<PagingData<Video>> {
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = TVShowsRemoteMediator(
                moviesAPI = moviesAPI,
                moviesDB = moviesDB,
                tvShowsRemoteKeysDao = tvShowsRemoteKeysDao,
                tvShowsDao = tvShowsDao,
            ),
            pagingSourceFactory = { tvShowsDao.getAllTVShows() }
        ).flow.map {
            it.map { video -> video.asDomainModel() }
        }
    }

    override suspend fun getVideoInfo(videoId: Int): Video {
        if (networkHandler.isOnline()) {
            val tvShow = moviesAPI.getTVShowInfo(videoId).asDomainModel()
            tvShowsDao.update(tvShow.asTVShowDatabaseModel())

        }
        return tvShowsDao.getTVShowById(videoId).asDomainModel()
    }

    override suspend fun getVideoClips(videoId: Int): List<Clip> {
        if (networkHandler.isOnline()) {
            val clips = moviesAPI.getTVShowClips(videoId).asDomainModel()
            tvShowClipsDao.insert(clips.asTVShowClipsDatabaseModel())
        }
        return tvShowClipsDao.getClips(videoId).asDomainModel()
    }

    override fun getVideoReviews(videoId: Int): Flow<PagingData<Review>> {
        TODO("Not yet implemented")
    }

}

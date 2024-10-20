package com.example.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.db.dao.TVShowsDao
import com.example.movies.data.local.db.dao.TVShowsRemoteKeysDao
import com.example.movies.data.local.models.remotekeys.TVShowRemoteKeys
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asTVShowDatabaseModel

@ExperimentalPagingApi
class TVShowsRemoteMediator(
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
    private val tvShowsRemoteKeysDao: TVShowsRemoteKeysDao,
    private val tvShowsDao: TVShowsDao,
) : RemoteMediator<Int, LocalTVShow>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalTVShow>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = moviesAPI.getTVShows(page = currentPage)

            val endOfPaginationReached = response.results?.isEmpty() ?: true

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            moviesDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tvShowsDao.clearTVShows()
                    tvShowsRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.results
                    ?.asSequence()
                    ?.filterNotNull()
                    ?.map {
                        TVShowRemoteKeys(
                            id = it.id!!,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    ?.toList() ?: emptyList()
                tvShowsRemoteKeysDao.insert(remoteKeys = keys)
                tvShowsDao.insert(tvShows = response.asTVShowDatabaseModel())
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalTVShow>
    ): TVShowRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tvShowsRemoteKeysDao.getRemoteKeysById(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LocalTVShow>
    ): TVShowRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { localVideo ->
                tvShowsRemoteKeysDao.getRemoteKeysById(id = localVideo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LocalTVShow>
    ): TVShowRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { localVideo ->
                tvShowsRemoteKeysDao.getRemoteKeysById(id = localVideo.id)
            }
    }

}

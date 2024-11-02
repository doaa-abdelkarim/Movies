package com.example.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.models.remotekeys.TVShowsRemoteKeys
import com.example.movies.data.local.models.LocalMovie
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDatabaseModel

@ExperimentalPagingApi
class TVShowsRemoteMediator(
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
) : RemoteMediator<Int, LocalMovie>() {

    private val tvShowsRemoteKeysDao = moviesDB.tvShowsRemoteKeysDao()
    private val moviesDao = moviesDB.moviesDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalMovie>
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
                    moviesDao.clearTVShows()
                    tvShowsRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.results
                    ?.asSequence()
                    ?.filterNotNull()
                    ?.map {
                        TVShowsRemoteKeys(
                            id = it.id!!,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    ?.toList() ?: emptyList()
                tvShowsRemoteKeysDao.insert(remoteKeys = keys)
                moviesDao.insert(movies = response.asDatabaseModel(false))
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalMovie>
    ): TVShowsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tvShowsRemoteKeysDao.getRemoteKeysById(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LocalMovie>
    ): TVShowsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { localMovie ->
                tvShowsRemoteKeysDao.getRemoteKeysById(id = localMovie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LocalMovie>
    ): TVShowsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { localMovie ->
                tvShowsRemoteKeysDao.getRemoteKeysById(id = localMovie.id)
            }
    }

}

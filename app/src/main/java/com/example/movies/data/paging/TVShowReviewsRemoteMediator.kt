package com.example.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.models.remotekeys.TVShowReviewsRemoteKeys
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview
import com.example.movies.data.remote.apis.APIConstants.Companion.DEFAULT_PAGE_INDEX
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asTVShowReviewsDatabaseModel

@ExperimentalPagingApi
class TVShowReviewsRemoteMediator(
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
    private val tvShowId: Int
) : RemoteMediator<Int, LocalTVShowReview>() {

    private val tvShowReviewsRemoteKeysDao = moviesDB.tvShowReviewsRemoteKeysDao()
    private val tvShowReviewsDao = moviesDB.tvShowReviewsDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalTVShowReview>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: DEFAULT_PAGE_INDEX
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

            val response = moviesAPI.getTVShowReviews(tvShowId = tvShowId, page = currentPage)

            val endOfPaginationReached = response.results.isNullOrEmpty()

            val prevPage = if (currentPage == DEFAULT_PAGE_INDEX) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            moviesDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    tvShowReviewsDao.clearReviews()
                    tvShowReviewsRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.results
                    ?.asSequence()
                    ?.filterNotNull()
                    ?.map {
                        TVShowReviewsRemoteKeys(
                            id = it.id!!,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    ?.toList() ?: emptyList()
                tvShowReviewsRemoteKeysDao.insert(remoteKeys = keys)
                tvShowReviewsDao.insert(reviews = response.asTVShowReviewsDatabaseModel())
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalTVShowReview>
    ): TVShowReviewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tvShowReviewsRemoteKeysDao.getRemoteKeysById(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LocalTVShowReview>
    ): TVShowReviewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { localVideo ->
                tvShowReviewsRemoteKeysDao.getRemoteKeysById(id = localVideo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LocalTVShowReview>
    ): TVShowReviewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { localVideo ->
                tvShowReviewsRemoteKeysDao.getRemoteKeysById(id = localVideo.id)
            }
    }

}

package com.example.movies.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.data.local.db.MoviesDB
import com.example.movies.data.local.models.LocalReview
import com.example.movies.data.local.models.remotekeys.TVShowReviewsRemoteKeys
import com.example.movies.data.remote.apis.APIConstants.Companion.DEFAULT_PAGE_INDEX
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.asDatabaseModel

@ExperimentalPagingApi
class TVShowReviewsRemoteMediator(
    private val moviesAPI: MoviesAPI,
    private val moviesDB: MoviesDB,
    private val id: Int
) : RemoteMediator<Int, LocalReview>() {

    private val tvShowReviewsRemoteKeysDao = moviesDB.tvShowReviewsRemoteKeysDao()
    private val reviewsDao = moviesDB.reviewsDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalReview>
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

            val response = moviesAPI.getTVShowReviews(tvShowId = id, page = currentPage)

            val endOfPaginationReached = response.results.isNullOrEmpty()

            val prevPage = if (currentPage == DEFAULT_PAGE_INDEX) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            moviesDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    reviewsDao.clearReviews(id = id)
                    tvShowReviewsRemoteKeysDao.clearRemoteKeys(id = id)
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
                reviewsDao.insert(reviews = response.asDatabaseModel())
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalReview>
    ): TVShowReviewsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.reviewId?.let { id ->
                tvShowReviewsRemoteKeysDao.getRemoteKeysById(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, LocalReview>
    ): TVShowReviewsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { localReview ->
                tvShowReviewsRemoteKeysDao.getRemoteKeysById(id = localReview.reviewId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, LocalReview>
    ): TVShowReviewsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { localReview ->
                tvShowReviewsRemoteKeysDao.getRemoteKeysById(id = localReview.reviewId)
            }
    }

}

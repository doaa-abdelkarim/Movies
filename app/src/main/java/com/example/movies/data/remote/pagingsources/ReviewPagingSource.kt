package com.example.movies.data.remote.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.remote.apis.APIConstants.Companion.DEFAULT_PAGE_INDEX
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.ReviewsResultsItem
import com.example.movies.util.VideoType
import com.example.movies.util.VideoType.MOVIE
import retrofit2.HttpException
import java.io.IOException

class ReviewPagingSource(
    private val moviesAPI: MoviesAPI,
    private val videoType: VideoType,
    private val videoId: Int
) : PagingSource<Int, ReviewsResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ReviewsResultsItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewsResultsItem> {
        val currentPage = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = if (videoType == MOVIE)
                moviesAPI.getMovieReviews(movieId = videoId, page = currentPage)
            else
                moviesAPI.getTVShowReviews(tvShowId = videoId, page = currentPage)
            LoadResult.Page(
                data = response.results as List<ReviewsResultsItem>,
                prevKey = if (currentPage == DEFAULT_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (response.results.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}
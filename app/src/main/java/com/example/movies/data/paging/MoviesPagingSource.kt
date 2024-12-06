package com.example.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.remote.apis.APIConstants.Companion.DEFAULT_PAGE_INDEX
import com.example.movies.data.remote.apis.MoviesAPI
import com.example.movies.data.remote.models.RemoteMovie
import com.example.movies.util.constants.enums.VideoType
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val moviesAPI: MoviesAPI,
    private val videoType: VideoType
) : PagingSource<Int, RemoteMovie>() {
    override fun getRefreshKey(state: PagingState<Int, RemoteMovie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteMovie> {
        val currentPage = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response =
                if (videoType == VideoType.MOVIE)
                    moviesAPI.getMovies(page = currentPage)
                else
                    moviesAPI.getTVShows(page = currentPage)
            LoadResult.Page(
                data = response.results as List<RemoteMovie>,
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
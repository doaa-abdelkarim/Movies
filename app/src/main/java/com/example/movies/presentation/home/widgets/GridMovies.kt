package com.example.movies.presentation.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.LazyPagingItems
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.home.draft.parent.CellMovie
import com.example.movies.ui.theme.darkerGray

@Composable
fun GridMovies(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Movie) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkerGray),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let {
                CellMovie(
                    movie = it,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

/*
@Composable
fun <T : Any> List<T>.toPagingItems(): LazyPagingItems<T> {
    val pagingSourceFactory = { object : PagingSource<Int, T>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
            return LoadResult.Page(data = this@toPagingItems, prevKey = null, nextKey = null)
        }

        override fun getRefreshKey(state: PagingState<Int, T>): Int? = null
    }}

    return Pager(PagingConfig(pageSize = 10), pagingSourceFactory = pagingSourceFactory).flow.collectAsLazyPagingItems()
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF212121)
@Composable
fun GridMoviesPreview() {
    GridMovies(
        listOf(
            Movie(
                id = 1,
                posterPath = "/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg",
                title = "Title jkhh j jj hj hj hj hj hjh jh jh jkh jkh jkhj hj hj hj hjk hkj",
                popularity = 20.0,
                isMovie = true
            ),
            Movie(
                id = 1,
                posterPath = "/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg",
                title = "Title jkhh j jj hj hj hj hj hjh jh jh jkh jkh jkhj hj hj hj hjk hkj",
                popularity = 20.0,
                isMovie = true
            ),
            Movie(
                id = 1,
                posterPath = "/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg",
                title = "Title jkhh j jj hj hj hj hj hjh jh jh jkh jkh jkhj hj hj hj hjk hkj",
                popularity = 20.0,
                isMovie = true
            ),
            Movie(
                id = 1,
                posterPath = "/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg",
                title = "Title jkhh j jj hj hj hj hj hjh jh jh jkh jkh jkhj hj hj hj hjk hkj",
                popularity = 20.0,
                isMovie = true
            ),
            Movie(
                id = 1,
                posterPath = "/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg",
                title = "Title jkhh j jj hj hj hj hj hjh jh jh jkh jkh jkhj hj hj hj hjk hkj",
                popularity = 20.0,
                isMovie = true
            )
        ).toPagingItems()
    )
}*/

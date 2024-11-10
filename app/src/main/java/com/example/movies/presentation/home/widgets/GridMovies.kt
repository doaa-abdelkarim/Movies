package com.example.movies.presentation.home.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.gridItemPagingLoadState
import com.example.movies.ui.theme.darkerGray
import com.example.movies.ui.theme.teal200

@Composable
fun GridMovies(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Movie) -> Unit,
) {
    val loadStateRefresh = movies.loadState.refresh
    if (loadStateRefresh == LoadState.Loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = teal200)
        }
    } else if (loadStateRefresh is LoadState.Error) {
        Toast
            .makeText(
                LocalContext.current,
                loadStateRefresh.error.localizedMessage ?: stringResource(
                    R.string.unknown_error
                ),
                Toast.LENGTH_LONG
            )
            .show()
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkerGray),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_small)),
    ) {
        gridItemPagingLoadState(
            loadState = movies.loadState.prepend,
            keySuffix = "prepend",
            loading = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = teal200)
                }
            },
            error = {
                Toast
                    .makeText(
                        LocalContext.current,
                        it.error.localizedMessage ?: stringResource(
                            R.string.unknown_error
                        ),
                        Toast.LENGTH_LONG
                    )
                    .show()
            },
        )

        items(movies.itemCount) { index ->
            movies[index]?.let {
                CellMovie(
                    movie = it,
                    onItemClick = onItemClick
                )
            }
        }

        gridItemPagingLoadState(
            loadState = movies.loadState.append,
            keySuffix = "append",
            loading = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = teal200)
                }
            },
            error = {
                Toast
                    .makeText(
                        LocalContext.current,
                        it.error.localizedMessage ?: stringResource(
                            R.string.unknown_error
                        ),
                        Toast.LENGTH_LONG
                    )
                    .show()
            },
        )
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

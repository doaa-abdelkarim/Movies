package com.example.movies.presentation.details.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.movies.domain.entities.Review
import com.example.movies.presentation.common.listItemPagingLoadState
import com.example.movies.ui.theme.darkerGray
import com.example.movies.ui.theme.teal200

@Composable
fun ListReviews(reviews: LazyPagingItems<Review>) {
    val loadStateRefresh = reviews.loadState.refresh
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkerGray),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_normal))
    ) {
        listItemPagingLoadState(
            loadState = reviews.loadState.prepend,
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

        items(reviews.itemCount) { index ->
            reviews[index]?.let { CellReview(review = it) }
        }

        listItemPagingLoadState(
            loadState = reviews.loadState.append,
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
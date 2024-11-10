package com.example.movies.presentation.common

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState

fun LazyGridScope.gridItemPagingLoadState(
    loadState: LoadState,
    keySuffix: String? = null,
    loading: (@Composable LazyGridItemScope.() -> Unit)? = null,
    error: (@Composable LazyGridItemScope.(LoadState.Error) -> Unit)? = null,
) {
    if (loading != null && loadState == LoadState.Loading) {
        item(
            key = keySuffix?.let { "loadingItem_$it" },
            content = loading,
        )
    }
    if (error != null && loadState is LoadState.Error) {
        item(
            key = keySuffix?.let { "errorItem_$it" },
            content = { error(loadState) },
        )
    }
}
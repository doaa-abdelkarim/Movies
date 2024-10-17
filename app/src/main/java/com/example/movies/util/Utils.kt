package com.example.movies.util

import androidx.paging.PagingConfig
import com.example.movies.data.remote.apis.APIConstants

val <T> T.exhaustive: T
    get() = this

fun getDefaultPageConfig() = PagingConfig(
    pageSize = APIConstants.DEFAULT_PAGE_SIZE,
    enablePlaceholders = false
)

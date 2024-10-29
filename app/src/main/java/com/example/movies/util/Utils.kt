package com.example.movies.util

import android.net.Uri
import androidx.paging.PagingConfig
import com.example.movies.data.remote.apis.APIConstants
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_BASE_URL
import com.example.movies.util.AppConstants.Companion.YOUTUBE_IMAGE_HIGH_QUALITY

val <T> T.exhaustive: T
    get() = this

fun getDefaultPageConfig() = PagingConfig(
    pageSize = APIConstants.DEFAULT_PAGE_SIZE,
    enablePlaceholders = false
)

fun getYouTubeUriByKey(key:String) : Uri =
    Uri.parse(YOUTUBE_IMAGE_BASE_URL)
    .buildUpon()
    .appendPath(key)
    .appendPath(YOUTUBE_IMAGE_HIGH_QUALITY)
    .build()

package com.example.movies.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.paging.PagingConfig
import com.example.movies.data.remote.apis.APIConstants
import com.example.movies.util.constants.AppConstants.Companion.YOUTUBE_IMAGE_BASE_URL
import com.example.movies.util.constants.AppConstants.Companion.YOUTUBE_IMAGE_HIGH_QUALITY
import com.example.movies.util.extensions.findActivity

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

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

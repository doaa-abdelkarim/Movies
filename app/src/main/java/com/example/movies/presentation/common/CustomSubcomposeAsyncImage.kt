package com.example.movies.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.placeholder
import com.example.movies.R

@Composable
fun CustomSubcomposeAsyncImage(
    modifier: Modifier = Modifier,
    data: Any?,
    contentDescription: String?,
    error: @Composable (() -> Unit)? = null,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .placeholder(R.drawable.ic_placeholder_image)
            .crossfade(true)
            .crossfade(durationMillis = 1000)
            .build(),
//        placeholder = painterResource(R.drawable.ic_placeholder_author)
//        loading = {
//            CircularProgressIndicator(
//                color = teal200,
//                modifier = Modifier.size(10.dp)
//            )
//        },
        error = {
            error?.invoke() ?: Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.broken_image),
                alpha = 0.2f,
            )
        },
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
    )
}
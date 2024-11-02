package com.example.movies.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.movies.R
import com.example.movies.ui.theme.teal200

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
            .crossfade(true)
            .build(),
        loading = {
            CircularProgressIndicator(
                color = teal200,
                modifier = Modifier.scale(0.5f)
            )
        },
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
package com.example.movies.presentation.details.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.domain.entities.Clip
import com.example.movies.presentation.common.CustomSubcomposeAsyncImage
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.regularSize16White

@Composable
fun CellClip(
    clip: Clip,
    onItemClick: (Clip) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = gray700)
            .padding(all = dimensionResource(R.dimen.spacing_normal))
            .clickable {
                onItemClick(clip)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomSubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .aspectRatio(4 / 3f),
            data = clip.clipUri,
            contentDescription = stringResource(R.string.clip_image)
        )
        Text(
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.spacing_normal),
                end = dimensionResource(R.dimen.spacing_normal)
            ),
            text = clip.name ?: "-",
            style = regularSize16White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun CellClipPreview() {
    CellClip(
        clip = Clip(
            movieId = 1,
            clipId = "1",
            name = "Clip name"
        ),
        onItemClick = {}
    )
}
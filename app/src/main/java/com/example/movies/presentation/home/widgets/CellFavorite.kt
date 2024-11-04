package com.example.movies.presentation.home.widgets

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import com.example.movies.R
import com.example.movies.domain.entities.Favorite
import com.example.movies.presentation.common.CustomSubcomposeAsyncImage
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.regularSize16White

@Composable
fun CellFavorite(favorite: Favorite) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = gray700)
            .padding(all = dimensionResource(R.dimen.spacing_normal)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomSubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .aspectRatio(1.3f),
            data = favorite.posterUri,
            contentDescription = stringResource(R.string.movie_poster),
        )
        Text(
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.spacing_normal),
            ),
            text = favorite.title ?: "-",
            style = regularSize16White.copy(
                fontSize = 16.sp//dimensionResource(R.dimen.favorite_movie_title)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
package com.example.movies.presentation.home.parent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.normalSize18White
import com.example.movies.ui.theme.teal200

@Composable
fun CellMovie(
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = gray700)
            .clickable {
                onItemClick(movie)
            }
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.aspectRatio(2 / 3f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterUri)
                .crossfade(true)
                .build(),
            loading = {
                CircularProgressIndicator(
                    color = teal200,
                    modifier = Modifier.scale(0.5f)
                )

            },
            error = {
                Image(
                    painter = painterResource(R.drawable.ic_broken_image),
                    contentDescription = stringResource(R.string.broken_image),
                    alpha = 0.2f,
                )
            },
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.spacing_small)),
            text = movie.title ?: "-",
            style = normalSize18White,
            maxLines = 3,
            minLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.spacing_small)),
            text = stringResource(R.string.popularity, movie.popularity.toString()),
            style = normalSize18White
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CellMoviePreview() {
    CellMovie(
        movie = Movie(
            id = 1,
            posterPath = "/1MJNcPZy46hIy2CmSqOeru0yr5C.jpg",
            title = "Title",
            popularity = 20.0,
            isMovie = true
        ),
        onItemClick = {}
    )
}
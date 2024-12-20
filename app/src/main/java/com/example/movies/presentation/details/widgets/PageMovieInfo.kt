package com.example.movies.presentation.details.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.ui.theme.boldSize16VeryDarkGray
import com.example.movies.ui.theme.regularSize14LightGray

@Composable
fun PageMovieInfo(movie: Movie?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = dimensionResource(R.dimen.spacing_normal))
            .verticalScroll(state = rememberScrollState())
    ) {
        Text(
            text = movie?.overview ?: "",
            style = regularSize14LightGray
        )
        Row {
            Text(
                text = stringResource(R.string.genres),
                style = boldSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.genres ?: "",
                style = regularSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.released),
                style = boldSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.releaseDate ?: "",
                style = regularSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.original_title),
                style = boldSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.originalTitle ?: "",
                style = regularSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.original_language),
                style = boldSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.originalLanguage ?: "",
                style = regularSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.popularity),
                style = boldSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.popularity?.toString() ?: "",
                style = regularSize14LightGray
            )
        }
        if (movie?.isMovie == true)
            Row(modifier = Modifier) {
                Text(
                    text = stringResource(R.string.revenue),
                    style = boldSize16VeryDarkGray
                )
                Text(
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                    text = movie.revenue?.toString() ?: "",
                    style = regularSize14LightGray
                )
            }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF212121
)
@Composable
fun PageMovieInfoPreview() {
    PageMovieInfo(
        Movie(
            pk = 1,
            id = 533535,
            posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
            title = "Deadpool & Wolverine",
            popularity = 1636.995,
            genres = null,
            originalLanguage = "en",
            overview = "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
            releaseDate = "2024-07-24",
            originalTitle = "Deadpool & Wolverine",
            revenue = 1336816112,
            isMovie = true
        )
    )
}
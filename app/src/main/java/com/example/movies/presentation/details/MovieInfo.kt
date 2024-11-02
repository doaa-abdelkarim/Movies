package com.example.movies.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.details.children.info.InfoViewModel
import com.example.movies.ui.theme.normalSize14LightGray
import com.example.movies.ui.theme.normalSize16VeryDarkGray

@Composable
fun MovieInfo(infoViewModel: InfoViewModel = hiltViewModel()) {
    val movie = infoViewModel.info.collectAsState().value
    ScreenContent(movie = movie)
}

@Composable
fun ScreenContent(movie: Movie?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = dimensionResource(R.dimen.spacing_normal))
            .verticalScroll(state = rememberScrollState())
    ) {
        Text(
            text = movie?.overview ?: "",
            style = normalSize14LightGray
        )
        Row {
            Text(
                text = stringResource(R.string.genres),
                style = normalSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.genres ?: "-",
                style = normalSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.released),
                style = normalSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.releaseDate ?: "-",
                style = normalSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.original),
                style = normalSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.originalTitle ?: "-",
                style = normalSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.original_language),
                style = normalSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.originalLanguage ?: "-",
                style = normalSize14LightGray
            )
        }
        Row {
            Text(
                text = stringResource(R.string.popularity),
                style = normalSize16VeryDarkGray
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                text = movie?.popularity?.toString() ?: "-",
                style = normalSize14LightGray
            )
        }
        if (movie?.isMovie == true)
            Row(modifier = Modifier) {
                Text(
                    text = stringResource(R.string.revenue),
                    style = normalSize16VeryDarkGray
                )
                Text(
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_small)),
                    text = movie.revenue?.toString() ?: "-",
                    style = normalSize14LightGray
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
fun ScreenContentPreview() {
    ScreenContent(
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
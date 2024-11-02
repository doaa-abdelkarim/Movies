package com.example.movies.presentation.home.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.CustomSubcomposeAsyncImage
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.normalSize18White

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
        CustomSubcomposeAsyncImage(
            modifier = Modifier.aspectRatio(2 / 3f),
            data = movie.posterUri,
            contentDescription = stringResource(R.string.movie_poster)
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
            text = stringResource(R.string.popularity_percentage, movie.popularity.toString()),
            style = normalSize18White
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CellMoviePreview() {
    CellMovie(
        movie = Movie(
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
        ),
        onItemClick = {}
    )
}
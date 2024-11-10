package com.example.movies.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.details.widgets.PageMovieInfo
import com.example.movies.presentation.details.widgets.PageMoviesClips
import com.example.movies.presentation.details.widgets.PageMoviesReviews
import com.example.movies.ui.theme.regularSize18White
import com.example.movies.ui.theme.strongPink
import com.example.movies.util.extensions.isLargeScreen
import java.util.Locale

@Composable
fun SectionMovieDetails(
    innerPadding: PaddingValues,
    movie: Movie? = null,
    onAddToFavoriteClick: (Movie) -> Unit,
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val tabsTitles = stringArrayResource(R.array.tabs_details_titles).toList()
    val pagerState = rememberPagerState(pageCount = { tabsTitles.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    Column(modifier = Modifier.padding(innerPadding)) {
        SectionHeader(modifier = Modifier.weight(1f), movie = movie)
        TabsMain(
            tabsTitles = tabsTitles,
            selectedTabIndex = selectedTabIndex,
            pagerState = pagerState
        )
        HorizontalPager(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> PageMovieInfo(movie = movie)
                1 -> PageMoviesClips(navigateToMoviePlayerScreen = navigateToMoviePlayerScreen)
                else -> PageMoviesReviews()
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.spacing_small),
                    end = dimensionResource(R.dimen.spacing_small),
                    bottom = dimensionResource(R.dimen.spacing_small)
                ),
            colors = buttonColors(
                containerColor = strongPink
            ),
            shape = RoundedCornerShape(
                corner = CornerSize(
                    size = 0.dp
                )
            ),
            onClick = { movie?.let { onAddToFavoriteClick(it) } }
        ) {
            Text(text = stringResource(R.string.add_to_favorites).uppercase(Locale.ROOT))
        }
    }
}

@Composable
private fun SectionHeader(modifier: Modifier, movie: Movie?) {
    Box(modifier = modifier) {
        CustomSubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            data = movie?.backdropUri,
            contentDescription = stringResource(R.string.movie_backdrop)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomStart),
        ) {
            CustomSubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = dimensionResource(R.dimen.spacing_large))
                    .aspectRatio(ratio = 0.5f),
                data = movie?.posterUri,
                contentDescription = stringResource(R.string.movie_poster)
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.spacing_large),
                        top = dimensionResource(R.dimen.spacing_small),
                        bottom = if (LocalContext.current.isLargeScreen())
                            dimensionResource(R.dimen.spacing_small)
                        else
                            dimensionResource(R.dimen.spacing_large)
                    )
                    .align(Alignment.Bottom),
                text = movie?.title ?: "",
                style = regularSize18White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
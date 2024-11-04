package com.example.movies.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.details.components.PageMovieInfo
import com.example.movies.presentation.details.components.PageMoviesClips
import com.example.movies.presentation.details.components.PageMoviesReviews
import com.example.movies.ui.theme.regularSize18White

@Composable
fun DetailsContent(
    movie: Movie? = null,
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val tabsTitles = stringArrayResource(R.array.tabs_details_titles).toList()
    val pagerState = rememberPagerState(pageCount = { tabsTitles.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    Column {
        DetailsHeader(modifier = Modifier.weight(1f), movie = movie)
        MainTabs(
            tabsTitles = tabsTitles,
            selectedTabIndex = selectedTabIndex,
            pagerState = pagerState
        )
        HorizontalPager(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = pagerState,
        ) {
            when (selectedTabIndex) {
                0 -> PageMovieInfo(movie = movie)
                1 -> PageMoviesClips(navigateToMoviePlayerScreen = navigateToMoviePlayerScreen)
                else -> PageMoviesReviews()
            }
        }
    }
}

@Composable
fun DetailsHeader(modifier: Modifier, movie: Movie?) {
    Box(modifier = modifier) {
        Column {
            CustomSubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                data = movie?.backdropUri,
                contentDescription = stringResource(R.string.movie_backdrop)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomStart),
        ) {
            CustomSubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(ratio = 0.55f)
                    .padding(start = dimensionResource(R.dimen.spacing_large)),
                data = movie?.posterUri,
                contentDescription = stringResource(R.string.movie_poster)
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.spacing_large),
                        top = dimensionResource(R.dimen.spacing_small),
                        bottom = dimensionResource(R.dimen.spacing_large)
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
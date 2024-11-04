package com.example.movies.presentation.details.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.CustomSubcomposeAsyncImage
import com.example.movies.presentation.common.MainTabs
import com.example.movies.presentation.details.components.PageMovieInfo
import com.example.movies.presentation.details.components.PageMoviesClips
import com.example.movies.presentation.details.components.PageMoviesReviews
import com.example.movies.ui.theme.regularSize18White
import com.example.movies.ui.theme.strongPink
import com.example.movies.ui.theme.transparent
import com.example.movies.ui.theme.white
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movie: Movie?,
    onAddToFavoriteClick: (Movie) -> Unit,
    navigateToMoviePlayerScreen: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    val tabsTitles = stringArrayResource(R.array.tabs_details_titles).toList()
    val pagerState = rememberPagerState(pageCount = { tabsTitles.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                colors = topAppBarColors(
                    containerColor = transparent,
                    navigationIconContentColor = white
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.spacing_small),
                ),
                containerColor = transparent
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
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
    ) { innerPadding ->
        Column {
            DetailsHeader(modifier = Modifier.weight(1f), movie = movie)
            MainTabs(
                tabsTitles = tabsTitles,
                selectedTabIndex = selectedTabIndex.value,
                pagerState = pagerState
            )
            DetailsHorizontalPager(
                modifier = Modifier.weight(1f),
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex.value,
                movie = movie,
                navigateToMoviePlayerScreen = navigateToMoviePlayerScreen
            )
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
                text = movie?.title ?: "-",
                style = regularSize18White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DetailsHorizontalPager(
    modifier: Modifier,
    pagerState: PagerState,
    selectedTabIndex: Int,
    navigateToMoviePlayerScreen: (String) -> Unit,
    movie: Movie?
) {
    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = pagerState,
    ) {
        when (selectedTabIndex) {
            0 -> PageMovieInfo(movie = movie)
            1 -> PageMoviesClips(navigateToMoviePlayerScreen = navigateToMoviePlayerScreen)
            else -> PageMoviesReviews()
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        movie = Movie(
            pk = 1,
            id = 533535,
            posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
            title = "Deadpool & Wolverine",
            popularity = 1636.995,
            genres = null,
            originalLanguage = "en",
            overview = "A listless Wade Wilson toils away in civilian life with his days as the " +
                    "morally flexible mercenary, Deadpool, behind him. But when his homeworld " +
                    "faces an existential threat, Wade must reluctantly suit-up again with an " +
                    "even more reluctant Wolverine.",
            releaseDate = "2024-07-24",
            originalTitle = "Deadpool & Wolverine",
            revenue = 1336816112,
            isMovie = true
        ),
        onAddToFavoriteClick = {},
        navigateToMoviePlayerScreen = {},
        navigateBack = {}
    )
}
package com.example.movies.presentation.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.R
import com.example.movies.presentation.common.MovieDetails
import com.example.movies.presentation.details.draft.parent.DetailsViewModel
import com.example.movies.presentation.home.draft.children.movies.MoviesViewModel
import com.example.movies.ui.theme.darkerGray

@Composable
fun Movies(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit,
    innerPadding: PaddingValues,
) {
    val movies = moviesViewModel.videosFlow.collectAsLazyPagingItems()
    val movie = detailsViewModel.movie.collectAsState().value
    LaunchedEffect(Unit) {
        moviesViewModel.observedVideo.collect {
            detailsViewModel.updateObservedMovie(movie = it)
            it?.let {
                detailsViewModel.getMovieDetails(
                    observedMovie = it,
                    isLargeScreen = true
                )
            }
        }
    }
    Row(modifier = Modifier.padding(innerPadding)) {
        Box(
            modifier = Modifier
                .background(color = darkerGray)
                .padding(
                    top = dimensionResource(R.dimen.spacing_small),
                    end = dimensionResource(R.dimen.spacing_small)
                )
                .weight(1f)
        ) {
            GridMovies(
                movies = movies,
                onItemClick = { movie -> moviesViewModel.onVideoClick(movie) }
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            MovieDetails(
                movie = movie,
                navigateToMoviePlayerScreen = navigateToMoviePlayerScreen
            )
        }
    }
}
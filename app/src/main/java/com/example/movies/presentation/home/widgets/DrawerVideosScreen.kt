package com.example.movies.presentation.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.SectionMovieDetails
import com.example.movies.presentation.details.viewmodels.DetailsViewModel
import com.example.movies.presentation.home.viewmodels.BaseVideosViewModel
import com.example.movies.presentation.home.viewmodels.MoviesViewModel
import com.example.movies.presentation.home.viewmodels.TVShowsViewModel
import com.example.movies.ui.theme.darkerGray
import com.example.movies.util.constants.enums.VideoType
import com.example.movies.util.extensions.isLargeScreen

@Composable
fun DrawerVideosScreen(
    innerPadding: PaddingValues,
    videoType: VideoType,
    baseVideosViewModel: BaseVideosViewModel =
        if (videoType == VideoType.MOVIE)
            hiltViewModel<MoviesViewModel>()
        else
            hiltViewModel<TVShowsViewModel>(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    onAddToFavoriteClick: (Movie) -> Unit,
    navigateToMoviePlayerScreen: (String) -> Unit,
) {
    val movies = baseVideosViewModel.videosFlow.collectAsLazyPagingItems().apply {
        if (LocalContext.current.isLargeScreen() &&
            baseVideosViewModel.observedVideo.value == null &&
            this.itemCount > 0
        )
            baseVideosViewModel.updateObservedVideo(video = peek(0))
    }
    val movieUiState by detailsViewModel.movie.collectAsState()
    LaunchedEffect(Unit) {
        baseVideosViewModel.observedVideo.collect {
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
                .fillMaxHeight()
                .background(color = darkerGray)
                .padding(
                    top = dimensionResource(R.dimen.spacing_small),
                    end = dimensionResource(R.dimen.spacing_small)
                )
                .weight(1f)
        ) {
            GridMovies(
                movies = movies,
                onItemClick = { movie -> baseVideosViewModel.onVideoClick(movie) }
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            SectionMovieDetails(
                innerPadding = PaddingValues(),
                movieUiState = movieUiState,
                onAddToFavoriteClick = onAddToFavoriteClick,
                navigateToMoviePlayerScreen = navigateToMoviePlayerScreen,
            )
        }
    }
}
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.R
import com.example.movies.presentation.common.SectionMovieDetails
import com.example.movies.presentation.details.draft.parent.DetailsViewModel
import com.example.movies.presentation.home.draft.base.VideosViewModel
import com.example.movies.presentation.home.draft.children.movies.MoviesViewModel
import com.example.movies.presentation.home.draft.children.tvshows.TVShowsViewModel
import com.example.movies.ui.theme.darkerGray
import com.example.movies.util.constants.enums.VideoType
import com.example.movies.util.extensions.isLargeScreen

@Composable
fun Movies(
    innerPadding: PaddingValues,
    videoType: VideoType,
    videosViewModel: VideosViewModel =
        if (videoType == VideoType.MOVIE)
            hiltViewModel<MoviesViewModel>()
        else
            hiltViewModel<TVShowsViewModel>(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit,
) {
    val movies = videosViewModel.videosFlow.collectAsLazyPagingItems().apply {
        if (LocalContext.current.isLargeScreen() &&
            videosViewModel.observedVideo.value == null &&
            this.itemCount > 0
        )
            videosViewModel.updateObservedVideo(video = peek(0))
    }
    val movie = detailsViewModel.movie.collectAsState().value
    LaunchedEffect(Unit) {
        videosViewModel.observedVideo.collect {
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
                onItemClick = { movie -> videosViewModel.onVideoClick(movie) }
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            SectionMovieDetails(
                movie = movie,
                navigateToMoviePlayerScreen = navigateToMoviePlayerScreen
            )
        }
    }
}
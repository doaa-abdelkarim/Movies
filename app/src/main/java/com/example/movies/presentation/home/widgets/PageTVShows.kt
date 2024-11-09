package com.example.movies.presentation.home.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.home.viewmodels.VideosEvent
import com.example.movies.presentation.home.viewmodels.TVShowsViewModel
import com.example.movies.util.exhaustive

@Composable
fun PageTVShows(
    tvShowsViewModel: TVShowsViewModel = hiltViewModel(),
    navigateToDetailsScreen: (Movie) -> Unit
) {
    val tvShows = tvShowsViewModel.videosFlow.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        tvShowsViewModel.videosEvent.collect {
            when (it) {
                is VideosEvent.NavigateToDetailsScreen -> navigateToDetailsScreen(it.video)
            }.exhaustive
        }
    }
    GridMovies(
        movies = tvShows,
        onItemClick = { tvShow -> tvShowsViewModel.onVideoClick(tvShow) }
    )
}
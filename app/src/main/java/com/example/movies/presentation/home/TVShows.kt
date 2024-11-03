package com.example.movies.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.home.base.VideosEvent
import com.example.movies.presentation.home.children.tvshows.TVShowsViewModel
import com.example.movies.util.exhaustive

@Composable
fun TVShows(
    tvShowsViewModel: TVShowsViewModel = hiltViewModel(),
    navigateToDetailsScreen: (Movie) -> Unit
) {
    val tvShows = tvShowsViewModel.videosFlow.collectAsLazyPagingItems()
    LaunchedEffect(true) {
        tvShowsViewModel.videosEvent.collect {
            when (it) {
                is VideosEvent.NavigateToDetailsScreen -> navigateToDetailsScreen(it.video)
            }.exhaustive
        }
    }
    GridMovies(
        movies = tvShows,
        onItemClick = { tvShow -> tvShowsViewModel.onVideoClicked(tvShow) }
    )
}
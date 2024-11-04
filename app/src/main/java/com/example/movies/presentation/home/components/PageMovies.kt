package com.example.movies.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.home.draft.base.VideosEvent
import com.example.movies.presentation.home.draft.children.movies.MoviesViewModel
import com.example.movies.util.exhaustive

@Composable
fun PageMovies(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    navigateToDetailsScreen: (Movie) -> Unit
) {
    val movies = moviesViewModel.videosFlow.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        moviesViewModel.videosEvent.collect {
            when (it) {
                is VideosEvent.NavigateToDetailsScreen -> navigateToDetailsScreen(it.video)
            }.exhaustive
        }
    }
    GridMovies(
        movies = movies,
        onItemClick = { movie -> moviesViewModel.onVideoClick(movie) }
    )
}
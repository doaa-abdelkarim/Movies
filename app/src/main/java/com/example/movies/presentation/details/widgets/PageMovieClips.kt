package com.example.movies.presentation.details.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.presentation.details.viewmodels.ClipsEvent
import com.example.movies.presentation.details.viewmodels.ClipsViewModel
import com.example.movies.presentation.details.viewmodels.DetailsViewModel
import com.example.movies.util.exhaustive

@Composable
fun PageMoviesClips(
    clipsViewModel: ClipsViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val clips by clipsViewModel.clips.collectAsState()
    LaunchedEffect(Unit) {
        clipsViewModel.clipsEvent.collect {
            when (it) {
                is ClipsEvent.EventNavigateToMoviePlayerScreen -> {
                    navigateToMoviePlayerScreen(it.clipKey ?: "")
                }
            }.exhaustive
        }
    }
    LaunchedEffect(Unit) {
        detailsViewModel.observedMovie.collect {
            it?.let {
                clipsViewModel.getMovieClips(
                    observedMovie = it,
                    isLargeScreen = true
                )
            }
        }
    }
    ListClips(
        clips = clips,
        onItemClick = { clip -> clipsViewModel.onClipClick(clip) }
    )
}
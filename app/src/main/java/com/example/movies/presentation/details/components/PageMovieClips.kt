package com.example.movies.presentation.details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.presentation.details.draft.children.clips.ClipsEvent
import com.example.movies.presentation.details.draft.children.clips.ClipsViewModel
import com.example.movies.presentation.details.draft.parent.DetailsViewModel
import com.example.movies.util.exhaustive

@Composable
fun PageMoviesClips(
    clipsViewModel: ClipsViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val clips = clipsViewModel.clips.collectAsState().value
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
package com.example.movies.presentation.details.widgets

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.R
import com.example.movies.presentation.details.viewmodels.ClipsEvent
import com.example.movies.presentation.details.viewmodels.ClipsViewModel
import com.example.movies.presentation.details.viewmodels.DetailsViewModel
import com.example.movies.presentation.home.UiState
import com.example.movies.ui.theme.teal200
import com.example.movies.util.exhaustive

@Composable
fun PageMoviesClips(
    clipsViewModel: ClipsViewModel = hiltViewModel(),
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val clipsUiState = clipsViewModel.clips.collectAsState().value
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
    when (clipsUiState) {
        is UiState.Initial -> {}
        is UiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = teal200)
            }
        }

        is UiState.Data -> {
            ListClips(
                clips = clipsUiState.data,
                onItemClick = { clip -> clipsViewModel.onClipClick(clip) }
            )
        }

        is UiState.Error -> {
            Toast
                .makeText(
                    LocalContext.current,
                    clipsUiState.error.localizedMessage ?: stringResource(
                        R.string.unknown_error
                    ),
                    Toast.LENGTH_LONG
                )
                .show()
        }
    }
}
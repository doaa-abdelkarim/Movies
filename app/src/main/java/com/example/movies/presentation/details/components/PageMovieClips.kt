package com.example.movies.presentation.details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies.presentation.details.draft.children.clips.ClipsEvent
import com.example.movies.presentation.details.draft.children.clips.ClipsViewModel
import com.example.movies.util.exhaustive

@Composable
fun PageMoviesClips(
    clipsViewModel: ClipsViewModel = hiltViewModel(),
    navigateToMoviePlayerScreen: (String) -> Unit
) {
    val clips = clipsViewModel.clips.collectAsState().value
    LaunchedEffect(Unit) {
        clipsViewModel.clipsEvent.collect {
            when (it) {
                is ClipsEvent.EventNavigateToMoviePlayerScreen -> {
                    /*                    if ((appContext as MoviesApp).isLargeScreen)
                                            if (findNavController().currentDestination?.id == R.id.moviesFragment)
                                                findNavController().navigate(
                                                    MoviesFragmentDirections.actionMoviesFragmentToVideoPlayerFragment(
                                                        it.clipKey
                                                    )
                                                )
                                            else
                                                findNavController().navigate(
                                                    TVShowsFragmentDirections.actionTvShowsFragmentToVideoPlayerFragment(
                                                        it.clipKey
                                                    )
                                                )
                                        else*/
                    navigateToMoviePlayerScreen(it.clipKey ?: "")
                }
            }.exhaustive
        }
    }
    ListClips(
        clips = clips,
        onItemClick = { clip -> clipsViewModel.onClipClick(clip) }
    )
}
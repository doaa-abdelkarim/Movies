package com.example.movies.presentation.details.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.presentation.navigation.Screen
import com.example.movies.presentation.home.UiState
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClipsViewModel @Inject constructor(
    private val baseMoviesRepository: BaseMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedMovieId = savedStateHandle.toRoute<Screen.Details>().movieId
    private val isMovie = savedStateHandle.toRoute<Screen.Details>().isMovie

    private val _clips = MutableStateFlow<UiState<List<Clip>>>(UiState.Initial)
    val clips = _clips.asStateFlow()

    private val _clipsEventFlow = MutableSharedFlow<ClipsEvent>()
    val clipsEvent = _clipsEventFlow.asSharedFlow()

    init {
        if (selectedMovieId != null && selectedMovieId != -1 && isMovie != null)
            getMovieClips(
                selectedMovieId = selectedMovieId,
                isMovie = isMovie
            )
    }

    fun getMovieClips(observedMovie: Movie, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<Int>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != observedMovie.id) {
            getMovieClips(
                selectedMovieId = observedMovie.id,
                isMovie = observedMovie.isMovie,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = observedMovie.id
                }
            )

        }
    }

    private fun getMovieClips(
        selectedMovieId: Int,
        isMovie: Boolean,
        doForLargeScreen: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            _clips.value = UiState.Loading
            try {
                _clips.value = if (isMovie)
                    UiState.Data(data = baseMoviesRepository.getMovieClips(selectedMovieId))
                else
                    UiState.Data(data = baseMoviesRepository.getTVShowClips(selectedMovieId))
                doForLargeScreen?.invoke()
            } catch (e: Exception) {
                _clips.value = UiState.Error(error = e)
            }
        }
    }

    fun onClipClick(clip: Clip) {
        viewModelScope.launch {
            _clipsEventFlow.emit(
                ClipsEvent.EventNavigateToMoviePlayerScreen(
                    clip.key,
                    clip.name
                )
            )
        }
    }
}

sealed class ClipsEvent {
    data class EventNavigateToMoviePlayerScreen(
        val clipKey: String?, val clipName: String?
    ) : ClipsEvent()
}




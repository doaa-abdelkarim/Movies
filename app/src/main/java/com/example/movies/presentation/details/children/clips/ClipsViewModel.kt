package com.example.movies.presentation.details.children.clips

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.MovieNavType
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.presentation.navigation.Screen
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class ClipsViewModel @Inject constructor(
    private val baseMoviesRepository: BaseMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val selectedVideo = savedStateHandle.toRoute<Screen.Details>(
        typeMap = mapOf(
            typeOf<Movie?>() to MovieNavType
        )
    ).movie
    private val _clips = MutableStateFlow<List<Clip>>(emptyList())
    val clips = _clips.asStateFlow()

    private val _clipsEventFlow = MutableSharedFlow<ClipsEvent>()
    val clipsEvent = _clipsEventFlow.asSharedFlow()

    init {
        selectedVideo?.let { getVideoClips(it) }
    }

    fun getVideoClips(selectedVideo: Movie, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<Movie?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedVideo) {
            getVideoClips(
                selectedVideo = selectedVideo,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = selectedVideo
                }
            )

        }
    }

    private fun getVideoClips(
        selectedVideo: Movie,
        doForLargeScreen: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                _clips.value = if (selectedVideo.isMovie)
                    baseMoviesRepository.getMovieClips(selectedVideo.id)
                else
                    baseMoviesRepository.getTVShowClips(selectedVideo.id)
                doForLargeScreen?.invoke()
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }

        }
    }

    fun onClipClicked(clip: Clip) {
        viewModelScope.launch {
            _clipsEventFlow.emit(
                ClipsEvent.EventNavigateToVideoPlayerScreen(
                    clip.key,
                    clip.name
                )
            )
        }
    }
}

sealed class ClipsEvent {
    data class EventNavigateToVideoPlayerScreen(
        val clipKey: String?, val clipName: String?
    ) : ClipsEvent()
}




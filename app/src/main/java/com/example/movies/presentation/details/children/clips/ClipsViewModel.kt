package com.example.movies.presentation.details.children.clips

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClipsViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository,
    val state: SavedStateHandle
) : ViewModel() {

    private val selectedVideo = state.get<Video>(KEY_STATE_SELECTED_VIDEO)
    private val _clips = MutableStateFlow<List<Clip>>(emptyList())
    val clips = _clips.asStateFlow()

    private val _clipsEventFlow = MutableSharedFlow<ClipsEvent>()
    val clipsEvent = _clipsEventFlow.asSharedFlow()

    init {
        selectedVideo?.let { getVideoClips(it) }
    }

    fun getVideoClips(selectedVideo: Video, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = state.get<Video?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedVideo) {
            getVideoClips(
                selectedVideo = selectedVideo,
                doForLargeScreen = {
                    state[KEY_LAST_EMITTED_VALUE] = selectedVideo
                }
            )

        }
    }

    private fun getVideoClips(
        selectedVideo: Video,
        doForLargeScreen: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                _clips.value = if (selectedVideo is Movie)
                    moviesRepository.getVideoClips(selectedVideo.id)
                else
                    tvShowsRepository.getVideoClips(selectedVideo.id)
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




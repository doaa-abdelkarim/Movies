package com.example.movies.presentation.details.children.info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository,
    val state: SavedStateHandle
) : ViewModel() {
    private val selectedVideo = state.get<Video>(KEY_STATE_SELECTED_VIDEO)
    private val _info = MutableStateFlow<Video?>(null)
    val info = _info.asStateFlow()

    init {
        selectedVideo?.let { getVideoInfo(it) }
    }

    fun getVideoInfo(selectedVideo: Video, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = state.get<Video?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedVideo) {
            getVideoInfo(
                selectedVideo = selectedVideo,
                doForLargeScreen = {
                    state[KEY_LAST_EMITTED_VALUE] = selectedVideo
                }
            )

        }
    }

    fun getVideoInfo(selectedVideo: Video, doForLargeScreen: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {
                _info.value = if (selectedVideo is Movie)
                    moviesRepository.getVideoInfo(selectedVideo.id ?: -1)
                else
                    tvShowsRepository.getVideoInfo(selectedVideo.id ?: -1)
                doForLargeScreen?.invoke()
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }
    }
}



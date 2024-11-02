package com.example.movies.presentation.details.children.info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.MovieNavType
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.presentation.navigation.Screen
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val baseMoviesRepository: BaseMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val selectedVideo = savedStateHandle.toRoute<Screen.Details>(
        typeMap = mapOf(
            typeOf<Movie?>() to MovieNavType
        )
    ).movie
    private val _info = MutableStateFlow<Movie?>(null)
    val info = _info.asStateFlow()

    init {
        selectedVideo?.let { getVideoInfo(it) }
    }

    fun getVideoInfo(selectedVideo: Movie, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<Movie?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedVideo) {
            getVideoInfo(
                selectedVideo = selectedVideo,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = selectedVideo
                }
            )

        }
    }

    private fun getVideoInfo(
        selectedVideo: Movie,
        doForLargeScreen: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                _info.value = if (selectedVideo.isMovie)
                    baseMoviesRepository.getMovieInfo(selectedVideo.id)
                else
                    baseMoviesRepository.getTVShowInfo(selectedVideo.id)
                doForLargeScreen?.invoke()
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }
    }
}



package com.example.movies.presentation.home.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesApp
import com.example.movies.data.remote.apis.APIConstants.Companion.PAGE
import com.example.movies.domain.entities.Video
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class VideosViewModel(
    context: Context,
) : AndroidViewModel(context as Application) {

    var nextPage = PAGE
    var videosList = mutableListOf<Video>()

    private val _selectedVideo = MutableStateFlow<Video?>(null)
    val selectedVideo = _selectedVideo.asStateFlow()

    protected val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos = _videos.asStateFlow()

    private val _videosEventFlow = MutableSharedFlow<VideosEvent>()
    val videosEvent = _videosEventFlow.asSharedFlow()

    abstract fun getVideos()

    fun initializeFirstVideoAsDefaultSelectedVideoForLargeScreen() {
        if (getApplication<MoviesApp>().isLargeScreen &&
            _videos.value.isNotEmpty() &&
            _selectedVideo.value == null
        )
            _selectedVideo.value = _videos.value[0]
    }

    fun onVideoClicked(video: Video) {
        viewModelScope.launch {
            if (getApplication<MoviesApp>().isLargeScreen)
                _selectedVideo.value = video
            else
                _videosEventFlow.emit(VideosEvent.NavigateToDetailsScreen(video))
        }
    }

}

sealed class VideosEvent {
    data class NavigateToDetailsScreen(val video: Video) : VideosEvent()
}


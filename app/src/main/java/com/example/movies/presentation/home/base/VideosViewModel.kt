package com.example.movies.presentation.home.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movies.MoviesApp
import com.example.movies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class VideosViewModel(
    context: Context,
) : AndroidViewModel(context as Application) {

    abstract val videosFlow: Flow<PagingData<Movie>>

    protected abstract fun getVideos(): Flow<PagingData<Movie>>

    private val _videosEventFlow = MutableSharedFlow<VideosEvent>()
    val videosEvent = _videosEventFlow.asSharedFlow()

    /*
    In large devices, selectedMovie is observed. because details fragment is child of videos fragment
    */
    private val _observedVideo = MutableStateFlow<Movie?>(null)
    val observedVideo = _observedVideo.asStateFlow()

    fun updateObservedVideo(video: Movie?) {
        _observedVideo.value = video
    }

    fun onVideoClick(video: Movie) {
        viewModelScope.launch {
            if (getApplication<MoviesApp>().isLargeScreen)
                _observedVideo.value = video
            else
                _videosEventFlow.emit(VideosEvent.NavigateToDetailsScreen(video))
        }
    }

}

sealed class VideosEvent {
    data class NavigateToDetailsScreen(val video: Movie) : VideosEvent()
}


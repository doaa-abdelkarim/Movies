package com.example.movies.presentation.home.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesApp
import com.example.movies.data.remote.apis.APIConstants.Companion.PAGE
import com.example.movies.domain.entities.Video
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class VideosViewModel(
    context: Context,
) : AndroidViewModel(context as Application) {

    var nextPage = PAGE
    var videosList = mutableListOf<Video>()

    val _video = MutableLiveData<Video>()
    val video: LiveData<Video>
        get() = _video

    protected val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    private val videosEventChannel = Channel<VideosEvent>()
    val videoEvent = videosEventChannel.receiveAsFlow()

    abstract fun getVideos()

    fun onVideoClicked(video: Video) {
        viewModelScope.launch {
            if (getApplication<MoviesApp>().isLargeScreen)
                videosEventChannel.send(VideosEvent.PassVideoToDetailsScreen(video))
            else
                videosEventChannel.send(VideosEvent.NavigateToDetailsScreen(video))
        }
    }

}

sealed class VideosEvent {
    data class NavigateToDetailsScreen(val video: Video) : VideosEvent()
    data class PassVideoToDetailsScreen(val video: Video) : VideosEvent()
}


package com.example.movies.presentation.home.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.movies.MoviesApp
import com.example.movies.domain.models.Video
import com.example.movies.util.Constants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class VideosViewModel constructor(
    context: Context,
    state: SavedStateHandle
) : AndroidViewModel(context as Application) {

    var nextPage = Constants.PAGE
    var videosList = mutableListOf<Video>()

    val _video = MutableLiveData<Video>()
    val video: LiveData<Video>
        get() = _video

    protected val _videos: MutableLiveData<List<Video>> = state.getLiveData("_videos")
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
                videosEventChannel.send(VideosEvent.EventNavigateToDetailsScreen(video))
        }
    }

}

sealed class VideosEvent {
    data class EventNavigateToDetailsScreen(val video: Video) : VideosEvent()
    data class PassVideoToDetailsScreen(val video: Video) : VideosEvent()
}


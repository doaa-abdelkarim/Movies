package com.example.movies.presentation.home.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movies.MoviesApp
import com.example.movies.domain.entities.BaseVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class VideosViewModel(
    context: Context,
) : AndroidViewModel(context as Application) {

    abstract val videosFlow: Flow<PagingData<BaseVideo>>

    protected abstract fun getVideos(): Flow<PagingData<BaseVideo>>

    private val _videosEventFlow = MutableSharedFlow<VideosEvent>()
    val videosEvent = _videosEventFlow.asSharedFlow()

    val selectedVideo = MutableStateFlow<BaseVideo?>(null)

    fun onVideoClicked(video: BaseVideo) {
        viewModelScope.launch {
            if (getApplication<MoviesApp>().isLargeScreen)
                selectedVideo.value = video
            else
                _videosEventFlow.emit(VideosEvent.NavigateToDetailsScreen(video))
        }
    }

}

sealed class VideosEvent {
    data class NavigateToDetailsScreen(val video: BaseVideo) : VideosEvent()
}


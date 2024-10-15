package com.example.movies.presentation.details.children.clips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
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
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository
) : ViewModel() {

    private val _clips = MutableStateFlow<List<Clip>>(emptyList())
    val clips = _clips.asStateFlow()

    private val _clipsEventFlow = MutableSharedFlow<ClipsEvent>()
    val clipsEvent = _clipsEventFlow.asSharedFlow()

    fun getVideoClips(video: Video?) {
        viewModelScope.launch {
            if (video != null) {
                try {
                    _clips.value = if (video is Movie)
                        moviesRepository.getVideoClips(video.id ?: -1)
                    else
                        tvShowsRepository.getVideoClips(video.id ?: -1)
                } catch (e: Exception) {
                    Timber.d(e.localizedMessage)
                }
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




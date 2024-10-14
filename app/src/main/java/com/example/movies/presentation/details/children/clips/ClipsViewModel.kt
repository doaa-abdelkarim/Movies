package com.example.movies.presentation.details.children.clips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.Clip
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClipsViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository
) : ViewModel() {

    private val _clips = MutableLiveData<List<Clip>?>()
    val clips: LiveData<List<Clip>?>
        get() = _clips

    private val clipEventChannel = Channel<ClipsEvent>()
    val clipsEvent = clipEventChannel.receiveAsFlow()

    fun getVideoClips(video: Video?) {
        viewModelScope.launch {
            if (video != null) {
                try {
                    _clips.value = withContext(Dispatchers.IO) {
                        if (video is Movie)
                            moviesRepository.getVideoClips(video.id ?: -1)
                        else
                            tvShowsRepository.getVideoClips(video.id ?: -1)
                    }
                } catch (e: Exception) {
                    Timber.d(e.localizedMessage)
                }
            }
        }
    }

    fun onClipClicked(clip: Clip) {
        viewModelScope.launch {
            clipEventChannel.send(ClipsEvent.EventNavigateToVideoPlayerScreen(clip.key, clip.name))
        }
    }
}

sealed class ClipsEvent {
    data class EventNavigateToVideoPlayerScreen(val clipKey: String?, val clipName: String?) :
        ClipsEvent()
}




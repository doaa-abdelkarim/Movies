package com.example.movies.presentation.details.clips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.di.MoviesRepository
import com.example.movies.di.TVShowsRepository
import com.example.movies.domain.models.Clip
import com.example.movies.domain.models.Movie
import com.example.movies.domain.models.Video
import com.example.movies.domain.repositoriescontract.VideosRepositoryContract
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
    @MoviesRepository private val moviesRepository: VideosRepositoryContract,
    @TVShowsRepository private val tvShowsRepository: VideosRepositoryContract
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




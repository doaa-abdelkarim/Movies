package com.example.movies.presentation.details.children.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository
) : ViewModel() {

    private val _videoInfo = MutableStateFlow<Video?>(null)
    val videoInfo = _videoInfo.asStateFlow()

    fun getVideoInfo(video: Video?) {
        viewModelScope.launch {
            if (video != null)
                try {
                    _videoInfo.value = if (video is Movie)
                        moviesRepository.getVideoInfo(video.id ?: -1)
                    else
                        tvShowsRepository.getVideoInfo(video.id ?: -1)
                } catch (e: Exception) {
                    Timber.d(e.localizedMessage)
                }
        }
    }
}


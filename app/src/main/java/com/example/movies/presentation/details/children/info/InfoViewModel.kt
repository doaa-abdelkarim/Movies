package com.example.movies.presentation.details.children.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepository
import com.example.movies.data.di.TVShowsRepository
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepositoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    @MoviesRepository private val moviesRepository: BaseVideosRepositoryRepository,
    @TVShowsRepository private val tvShowsRepository: BaseVideosRepositoryRepository
) : ViewModel() {

    private val _video = MutableLiveData<Video?>()
    val video: LiveData<Video?>
        get() = _video


    fun getVideoInfo(video: Video?) {
        viewModelScope.launch {
            if (video != null)
                try {
                    _video.value = withContext(Dispatchers.IO) {
                        if (video is Movie)
                            moviesRepository.getVideoDetails(video.id ?: -1)
                        else
                            tvShowsRepository.getVideoDetails(video.id ?: -1)
                    }
                } catch (e: Exception) {
                    Timber.d(e.localizedMessage)
                }
        }
    }
}


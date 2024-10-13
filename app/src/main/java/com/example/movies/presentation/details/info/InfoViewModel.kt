package com.example.movies.presentation.details.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.di.MoviesRepository
import com.example.movies.di.TVShowsRepository
import com.example.movies.domain.models.Movie
import com.example.movies.domain.models.Video
import com.example.movies.domain.repositoriescontract.VideosRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    @MoviesRepository private val moviesRepository: VideosRepositoryContract,
    @TVShowsRepository private val tvShowsRepository: VideosRepositoryContract
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


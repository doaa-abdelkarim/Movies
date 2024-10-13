package com.example.movies.presentation.home.movies

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movies.di.MoviesRepository
import com.example.movies.domain.repositoriescontract.VideosRepositoryContract
import com.example.movies.presentation.home.base.VideosViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    @MoviesRepository private val videosRepository: VideosRepositoryContract,
    state: SavedStateHandle
) : VideosViewModel(context, state) {

    init {
        getVideos()
    }

    override fun getVideos() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    videosList.addAll(videosRepository.getVideos(nextPage))
                    _videos.postValue(videosList)
                }
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }
    }

}

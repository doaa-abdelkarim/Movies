package com.example.movies.presentation.home.tvshows

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movies.di.TVShowsRepository
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
class TVShowsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    @TVShowsRepository private val videosRepository: VideosRepositoryContract,
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

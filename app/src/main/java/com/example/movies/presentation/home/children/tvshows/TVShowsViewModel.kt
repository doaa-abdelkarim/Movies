package com.example.movies.presentation.home.children.tvshows

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.TVShowsRepository
import com.example.movies.domain.repositories.BaseVideosRepositoryRepository
import com.example.movies.presentation.home.base.VideosViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TVShowsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    @TVShowsRepository private val videosRepository: BaseVideosRepositoryRepository,
) : VideosViewModel(context) {

    init {
        getVideos()
    }

    override fun getVideos() {
        viewModelScope.launch {
            try {
                videosList.addAll(videosRepository.getVideos(nextPage))
                _videos.value = videosList
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }
    }

}

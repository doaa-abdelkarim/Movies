package com.example.movies.presentation.details.parent

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.data.local.models.favorites.LocalFavorite
import com.example.movies.domain.entities.BaseVideo
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.repositories.BaseFavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val favoritesRepository: BaseFavoritesRepository,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(context as Application) {

    /*
    In small devices, selectedVideo is passed as an argument because details fragment and videos
    fragment are not nested
     */
    private val selectedVideo = savedStateHandle.get<BaseVideo>("video")

    /*
    In large devices, selectedVideo is observed. because details fragment is child of videos fragment
     */
    private val _observableSelectedVideo = MutableStateFlow<BaseVideo?>(null)
    val observableSelectedVideo = _observableSelectedVideo.asStateFlow()

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _detailsEventFlow = MutableSharedFlow<DetailsEvent>()
    val detailsEvent = _detailsEventFlow.asSharedFlow()

    fun updateObservableSelectedVideo(selectedVideo: BaseVideo?) {
        _observableSelectedVideo.value = selectedVideo
    }

    fun onAddToFavorite(isLargeScreen: Boolean = false) {
        viewModelScope.launch {
            val selectedVideo =
                if (isLargeScreen) _observableSelectedVideo.value else this@DetailsViewModel.selectedVideo
            try {
                selectedVideo?.let {
                    favoritesRepository.cacheFavorite(
                        LocalFavorite(
                            videoId = it.id,
                            posterPath = it.posterPath,
                            backdropPath = it.backdropPath,
                            title = it.title,
                        )
                    )
                    _favorites.value = favoritesRepository.getAllFavorites()
                    _detailsEventFlow.emit(
                        DetailsEvent.ShowSavedMessage(
                            getApplication<MoviesApp>().getString(
                                R.string.saved
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }

        }
    }

    sealed class DetailsEvent {
        class ShowSavedMessage(val message: String) : DetailsEvent()
    }
}

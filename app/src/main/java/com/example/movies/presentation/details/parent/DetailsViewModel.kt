package com.example.movies.presentation.details.parent

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.FavoriteMoviesRepo
import com.example.movies.data.di.FavoriteTVShowsRepo
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.favorites.LocalFavoriteTVShow
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    @FavoriteMoviesRepo private val favoriteMoviesRepository: BaseFavoriteRepository,
    @FavoriteTVShowsRepo private val favoriteTVShowsRepository: BaseFavoriteRepository,
    @ApplicationContext context: Context,
    state: SavedStateHandle
) : AndroidViewModel(context as Application) {

    private val _selectedVideo = MutableStateFlow(state.get<Video>("video"))
    val selectedVideo = _selectedVideo.asStateFlow()

    private val type
        get() = _selectedVideo.value is Movie

    private val _favorites = MutableStateFlow<List<Video>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val detailsEventChannel = Channel<DetailsEvent>()
    val detailsEvent = detailsEventChannel.receiveAsFlow()

    fun updateSelectedVideo(selectedVideo: Video?) {
        _selectedVideo.value = selectedVideo
    }

    fun onAddFavorite() {
        viewModelScope.launch {
            try {
                if (type) {
                    favoriteMoviesRepository.cacheFavorites(
                        LocalFavoriteMovie(_selectedVideo.value?.id)
                    )
                } else {
                    favoriteTVShowsRepository.cacheFavorites(
                        LocalFavoriteTVShow(_selectedVideo.value?.id)
                    )
                }
                _favorites.value = favoriteMoviesRepository.getAllFavorites().plus(
                    favoriteTVShowsRepository.getAllFavorites()
                )
                detailsEventChannel.send(DetailsEvent.ShowSavedMessage("Saved"))
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }

        }
    }

    sealed class DetailsEvent {
        class ShowSavedMessage(val message: String) : DetailsEvent()
    }
}

package com.example.movies.presentation.details.parent

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.favorites.LocalFavoriteTVShow
import com.example.movies.data.di.FavoriteMoviesRepository
import com.example.movies.data.di.FavoriteTVShowsRepository
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    @FavoriteMoviesRepository private val favoriteMoviesRepository: BaseFavoriteRepository,
    @FavoriteTVShowsRepository private val favoriteTVShowsRepository: BaseFavoriteRepository,
    @ApplicationContext context: Context,
    private val state: SavedStateHandle
) : AndroidViewModel(context as Application) {

    var videoArg = state.get<Video>("video")
    private val type
        get() = videoArg is Movie

    private val _video = MutableLiveData<Video?>()
    val video: LiveData<Video?>
        get() = _video

    private val _favorites = MutableLiveData<List<Video>?>()
    val favorites: LiveData<List<Video>?>
        get() = _favorites

    private val detailsEventChannel = Channel<DetailsEvent>()
    val detailsEvent = detailsEventChannel.receiveAsFlow()

    fun updateVideoLiveData() {
        _video.value = videoArg
    }

    fun onAddFavorite() {
        viewModelScope.launch {
            try {
                _favorites.value = withContext(Dispatchers.IO) {
                    if (type) {
                        favoriteMoviesRepository.cacheFavorites(
                            LocalFavoriteMovie(videoArg?.id)
                        )
                        favoriteMoviesRepository.getAllFavorites()
                    } else {
                        favoriteTVShowsRepository.cacheFavorites(
                            LocalFavoriteTVShow(videoArg?.id)
                        )
                        favoriteTVShowsRepository.getAllFavorites()
                    }
                }
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

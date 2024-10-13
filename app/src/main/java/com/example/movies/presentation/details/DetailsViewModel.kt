package com.example.movies.presentation.details

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.movies.data.local.models.favorites.DatabaseFavoriteMovie
import com.example.movies.data.local.models.favorites.DatabaseFavoriteTVShow
import com.example.movies.di.FavoriteMoviesRepository
import com.example.movies.di.FavoriteTVShowsRepository
import com.example.movies.domain.models.Movie
import com.example.movies.domain.models.Video
import com.example.movies.domain.repositoriescontract.FavoriteRepositoryContract
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
    @FavoriteMoviesRepository private val favoriteMoviesRepository: FavoriteRepositoryContract,
    @FavoriteTVShowsRepository private val favoriteTVShowsRepository: FavoriteRepositoryContract,
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
                            DatabaseFavoriteMovie(videoArg?.id)
                        )
                        favoriteMoviesRepository.getAllFavorites()
                    } else {
                        favoriteTVShowsRepository.cacheFavorites(
                            DatabaseFavoriteTVShow(videoArg?.id)
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

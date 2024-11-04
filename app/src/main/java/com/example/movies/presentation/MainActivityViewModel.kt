package com.example.movies.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesApp
import com.example.movies.R
import com.example.movies.data.local.models.LocalFavorite
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.entities.Movie
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
class MainActivityViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val favoritesRepository: BaseFavoritesRepository,
) : AndroidViewModel(context as Application) {

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _mainEventFlow = MutableSharedFlow<MainEvent>()
    val mainEvent = _mainEventFlow.asSharedFlow()

    init {
        getAllFavorites()
    }

    fun onAddToFavoriteClick(movie: Movie) {
        viewModelScope.launch {
            try {
                favoritesRepository.cacheFavorite(
                    LocalFavorite(
                        movieId = movie.id,
                        posterPath = movie.posterPath,
                        backdropPath = movie.backdropPath,
                        title = movie.title,
                    )
                )
                _favorites.value = favoritesRepository.getAllFavorites()
                _mainEventFlow.emit(
                    MainEvent.ShowSavedMessage(
                        message = getApplication<MoviesApp>().getString(
                            R.string.saved
                        )
                    )
                )
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }

        }
    }

    private fun getAllFavorites() =
        viewModelScope.launch {
            try {
                _favorites.value = favoritesRepository.getAllFavorites()
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }


    sealed class MainEvent {
        class ShowSavedMessage(val message: String) : MainEvent()
    }
}




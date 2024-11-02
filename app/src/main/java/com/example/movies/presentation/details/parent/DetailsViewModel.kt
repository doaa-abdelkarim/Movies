package com.example.movies.presentation.details.parent

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
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
class DetailsViewModel @Inject constructor(
    private val favoritesRepository: BaseFavoritesRepository,
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(context as Application) {

    /*
    In small devices, selectedMovie is passed as an argument because details fragment and videos
    fragment are not nested
     */
    private val selectedMovie = savedStateHandle.get<Movie>("movie")

    /*
    In large devices, selectedMovie is observed. because details fragment is child of videos fragment
     */
    private val _observableSelectedMovie = MutableStateFlow<Movie?>(null)
    val observableSelectedMovie = _observableSelectedMovie.asStateFlow()

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _detailsEventFlow = MutableSharedFlow<DetailsEvent>()
    val detailsEvent = _detailsEventFlow.asSharedFlow()

    fun updateObservableSelectedMovie(selectedMovie: Movie?) {
        _observableSelectedMovie.value = selectedMovie
    }

    fun onAddToFavorite(isLargeScreen: Boolean = false) {
        viewModelScope.launch {
            val selectedMovie =
                if (isLargeScreen) _observableSelectedMovie.value else this@DetailsViewModel.selectedMovie
            try {
                selectedMovie?.let {
                    favoritesRepository.cacheFavorite(
                        LocalFavorite(
                            movieId = it.id,
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

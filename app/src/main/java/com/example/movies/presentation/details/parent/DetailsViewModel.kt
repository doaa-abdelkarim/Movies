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
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
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
    @ApplicationContext context: Context,
    private val baseMoviesRepository: BaseMoviesRepository,
    private val favoritesRepository: BaseFavoritesRepository,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(context as Application) {

    /*
    In small devices, selectedMovieId is passed as an argument because details fragment and videos
    fragment are not nested
     */
    private val selectedMovieId = savedStateHandle.get<Int>("movieId")
    private val isMovie = savedStateHandle.get<Boolean>("isMovie")

    /*
    In large devices, selectedMovie is observed. because details fragment is child of videos fragment
     */
    private val _observableSelectedMovie = MutableStateFlow<Movie?>(null)
    val observableSelectedMovie = _observableSelectedMovie.asStateFlow()

    private val _movieDetails = MutableStateFlow<Movie?>(null)
    val movieDetails = _movieDetails.asStateFlow()

    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _detailsEventFlow = MutableSharedFlow<DetailsEvent>()
    val detailsEvent = _detailsEventFlow.asSharedFlow()

    init {
        if (selectedMovieId != null && isMovie == true)
            getMovieDetails(
                selectedMovieId = selectedMovieId,
                isMovie = isMovie
            )
    }

    fun getMovieDetails(selectedMovie: Movie, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<Int>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedMovie.id) {
            getMovieDetails(
                selectedMovieId = selectedMovie.id,
                isMovie = selectedMovie.isMovie,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = selectedMovie.id

                }
            )

        }
    }

    private fun getMovieDetails(
        selectedMovieId: Int,
        isMovie: Boolean,
        doForLargeScreen: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            try {
                _movieDetails.value = if (isMovie)
                    baseMoviesRepository.getMovieDetails(selectedMovieId)
                else
                    baseMoviesRepository.getTVShowDetails(selectedMovieId)
                doForLargeScreen?.invoke()
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }

        }
    }


    fun updateObservableSelectedMovie(selectedMovie: Movie?) {
        _observableSelectedMovie.value = selectedMovie
    }

    fun onAddToFavorite() {
        viewModelScope.launch {
            try {
                _movieDetails.value?.let {
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

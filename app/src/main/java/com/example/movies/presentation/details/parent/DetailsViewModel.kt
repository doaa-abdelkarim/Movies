package com.example.movies.presentation.details.parent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val baseMoviesRepository: BaseMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

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

    init {
        if (selectedMovieId != null && isMovie != null)
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

}

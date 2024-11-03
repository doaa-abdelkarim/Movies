package com.example.movies.presentation.details.children.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.repositories.BaseMoviesRepository
import com.example.movies.util.constants.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_IS_MOVIE
import com.example.movies.util.constants.AppConstants.Companion.KEY_STATE_MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val baseMoviesRepository: BaseMoviesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedMovieId = savedStateHandle.get<Int>(KEY_STATE_MOVIE_ID)
    private val isMovie = savedStateHandle.get<Boolean>(KEY_STATE_IS_MOVIE)

    private val _reviews = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviews = _reviews.asStateFlow()

    init {
        if (selectedMovieId != null && selectedMovieId != -1 && isMovie != null)
            getMovieReviews(
                selectedMovieId = selectedMovieId,
                isMovie = isMovie
            )
    }

    fun getMovieReviews(selectedMovie: Movie, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<Int>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedMovie.id) {
            getMovieReviews(
                selectedMovieId = selectedMovie.id,
                isMovie = selectedMovie.isMovie,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = selectedMovie.id
                }
            )
        }
    }

    private fun getMovieReviews(
        selectedMovieId: Int,
        isMovie: Boolean,
        doForLargeScreen: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            //As I see without caching it does not survive configuration even if we cache in Room
            val reviews = if (isMovie)
                baseMoviesRepository.getMovieReviews(selectedMovieId).cachedIn(viewModelScope)
            else
                baseMoviesRepository.getTVShowReviews(selectedMovieId).cachedIn(viewModelScope)
            reviews.distinctUntilChanged()
                .collectLatest {
                    _reviews.value = it
                    doForLargeScreen?.invoke()
                }
        }
    }
}

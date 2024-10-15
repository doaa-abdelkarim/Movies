package com.example.movies.presentation.details.children.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.data.remote.apis.APIConstants.Companion.PAGE
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val selectedVideo = state.get<Video>(KEY_STATE_SELECTED_VIDEO)
    private var reviewsList = mutableListOf<Review>()
    var nextPage = PAGE

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews.asStateFlow()

    init {
        getVideoReviews()
    }

    fun getVideoReviews() {
        viewModelScope.launch {
            if (selectedVideo != null) {
                try {
                    if (selectedVideo is Movie) {
                        reviewsList.addAll(
                            moviesRepository.getVideoReviews(
                                selectedVideo.id ?: -1,
                                nextPage
                            )
                        )
                        _reviews.value = reviewsList
                    } else {
                        reviewsList.addAll(
                            tvShowsRepository.getVideoReviews(
                                selectedVideo.id ?: -1,
                                nextPage
                            )
                        )
                        _reviews.value = reviewsList
                    }
                } catch (e: Exception) {
                    Timber.d(e.localizedMessage)
                }
            }
        }
    }

}

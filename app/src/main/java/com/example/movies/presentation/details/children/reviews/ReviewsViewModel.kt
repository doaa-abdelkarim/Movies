package com.example.movies.presentation.details.children.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.di.MoviesRepo
import com.example.movies.data.di.TVShowsRepo
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository,
    val state: SavedStateHandle
) : ViewModel() {

    private val selectedVideo = state.get<Video>(KEY_STATE_SELECTED_VIDEO)
    val reviewsFlow = selectedVideo?.let { getVideoReviews(it) }

    fun getVideoReviews(selectedVideo: Video, isLargeScreen: Boolean): Flow<PagingData<Review>>? {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = state.get<Video?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        return if (lastEmittedValue == null || lastEmittedValue != selectedVideo) {
            getVideoReviews(
                selectedVideo = selectedVideo,
                doForLargeScreen = {
                    state[KEY_LAST_EMITTED_VALUE] = selectedVideo
                }
            )
        } else null
    }

    fun getVideoReviews(
        selectedVideo: Video,
        doForLargeScreen: (() -> Unit)? = null
    ): Flow<PagingData<Review>> {
        val reviews = if (selectedVideo is Movie) {
            moviesRepository.getVideoReviews(selectedVideo.id ?: -1).cachedIn(viewModelScope)
        } else {
            tvShowsRepository.getVideoReviews(selectedVideo.id ?: -1).cachedIn(viewModelScope)
        }
        doForLargeScreen?.invoke()
        return reviews
    }
}

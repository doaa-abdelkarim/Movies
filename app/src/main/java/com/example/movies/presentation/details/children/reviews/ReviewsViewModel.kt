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
import com.example.movies.domain.entities.BaseVideo
import com.example.movies.domain.repositories.BaseVideosRepository
import com.example.movies.util.AppConstants.Companion.KEY_LAST_EMITTED_VALUE
import com.example.movies.util.AppConstants.Companion.KEY_STATE_SELECTED_VIDEO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    @MoviesRepo private val moviesRepository: BaseVideosRepository,
    @TVShowsRepo private val tvShowsRepository: BaseVideosRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedVideo = savedStateHandle.get<BaseVideo>(KEY_STATE_SELECTED_VIDEO)
    private val _reviews = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviews = _reviews.asStateFlow()

    init {
        selectedVideo?.let { getVideoReviews(it) }
    }

    fun getVideoReviews(selectedVideo: BaseVideo, isLargeScreen: Boolean) {
        // Retrieve the last emitted value from SavedStateHandle
        val lastEmittedValue = savedStateHandle.get<BaseVideo?>(KEY_LAST_EMITTED_VALUE)
        // Only send request if the current value is different from the last one stored
        if (lastEmittedValue == null || lastEmittedValue != selectedVideo) {
            getVideoReviews(
                selectedVideo = selectedVideo,
                doForLargeScreen = {
                    savedStateHandle[KEY_LAST_EMITTED_VALUE] = selectedVideo
                }
            )
        }
    }

    private fun getVideoReviews(
        selectedVideo: BaseVideo,
        doForLargeScreen: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            if (selectedVideo is Movie) {
                //Use the next code if network is the single source of truth
//            moviesRepository.getVideoReviews(selectedVideo.id).cachedIn(viewModelScope)

                //Use the next code if Room is the single source of truth
//            moviesRepository.getVideoReviews(selectedVideo.id)
                //As I see without caching it does not survive configuration change
                moviesRepository.getVideoReviews(selectedVideo.id).cachedIn(viewModelScope)
                    .distinctUntilChanged()
                    .collectLatest {
                        _reviews.value = it
                    }
            } else {
                //Use the next code if network is the single source of truth
//            tvShowsRepository.getVideoReviews(selectedVideo.id).cachedIn(viewModelScope)

                //Use the next code if Room is the single source of truth
//            tvShowsRepository.getVideoReviews(selectedVideo.id)
                //As I see without caching it does not survive configuration change
                tvShowsRepository.getVideoReviews(selectedVideo.id).cachedIn(viewModelScope)
                    .distinctUntilChanged()
                    .collectLatest {
                        _reviews.value = it
                    }
            }
            doForLargeScreen?.invoke()
        }
    }
}

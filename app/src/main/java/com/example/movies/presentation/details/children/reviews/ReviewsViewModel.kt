package com.example.movies.presentation.details.children.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.MoviesRepository
import com.example.movies.data.di.TVShowsRepository
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.entities.Review
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseVideosRepositoryRepository
import com.example.movies.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    @MoviesRepository private val moviesRepository: BaseVideosRepositoryRepository,
    @TVShowsRepository private val tvShowsRepository: BaseVideosRepositoryRepository
) : ViewModel() {

    var nextPage = Constants.PAGE
    var reviewsList = mutableListOf<Review>()

    private val _reviews = MutableLiveData<List<Review>?>()
    val reviews: LiveData<List<Review>?>
        get() = _reviews


    fun getVideoReviews(video: Video?) {
        viewModelScope.launch {
            if (video != null) {
                try {
                    withContext(Dispatchers.IO) {
                        if (video is Movie) {
                            reviewsList.addAll(
                                moviesRepository.getVideoReviews(
                                    video.id ?: -1,
                                    nextPage
                                )
                            )
                            _reviews.postValue(reviewsList)
                        } else {
                            reviewsList.addAll(
                                tvShowsRepository.getVideoReviews(
                                    video.id ?: -1,
                                    nextPage
                                )
                            )
                            _reviews.postValue(reviewsList)
                        }
                    }
                } catch (e: Exception) {
                    Timber.d(e.localizedMessage)
                }
            }
        }
    }

}

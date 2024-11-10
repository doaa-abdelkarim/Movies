package com.example.movies.presentation.home.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.entities.Movie
import com.example.movies.domain.repositories.BaseMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val baseMoviesRepository: BaseMoviesRepository,
) : BaseVideosViewModel(context) {
    override val videosFlow: Flow<PagingData<Movie>> = getVideos()

    override fun getVideos(): Flow<PagingData<Movie>> =
        //As I see without caching it does not survive configuration even if we cache in Room
        baseMoviesRepository.getMovies().cachedIn(viewModelScope)
}

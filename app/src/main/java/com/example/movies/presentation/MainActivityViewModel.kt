package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.FavoriteMoviesRepo
import com.example.movies.data.di.FavoriteTVShowsRepo
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @FavoriteMoviesRepo private val favoriteMoviesRepository: BaseFavoriteRepository,
    @FavoriteTVShowsRepo private val favoriteTVShowsRepository: BaseFavoriteRepository
) : ViewModel() {

    val favorites = MutableStateFlow<List<Video>>(emptyList())

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() =
        viewModelScope.launch {
            try {
                favorites.value = favoriteMoviesRepository.getAllFavorites().plus(
                    favoriteTVShowsRepository.getAllFavorites()
                )
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }
}




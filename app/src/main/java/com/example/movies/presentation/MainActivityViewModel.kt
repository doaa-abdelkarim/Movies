package com.example.movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.di.FavoriteMoviesRepo
import com.example.movies.data.di.FavoriteTVShowsRepo
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @FavoriteMoviesRepo private val favoriteMoviesRepository: BaseFavoriteRepository,
    @FavoriteTVShowsRepo private val favoriteTVShowsRepository: BaseFavoriteRepository
) : ViewModel() {

    val favorites = MutableLiveData<List<Video>?>()

    init {
        viewModelScope.launch {
            favorites.value = getAllFavorites()
        }
    }

    suspend fun getAllFavorites(): List<Video>? {
        return try {
            withContext(Dispatchers.IO) {
                favoriteMoviesRepository.getAllFavorites().plus(
                    favoriteTVShowsRepository.getAllFavorites()
                )
            }
        } catch (e: Exception) {
            Timber.d(e.localizedMessage)
            null
        }
    }

}




package com.example.movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.di.FavoriteMoviesRepository
import com.example.movies.di.FavoriteTVShowsRepository
import com.example.movies.domain.models.Video
import com.example.movies.domain.repositoriescontract.FavoriteRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @FavoriteMoviesRepository private val favoriteMoviesRepository: FavoriteRepositoryContract,
    @FavoriteTVShowsRepository private val favoriteTVShowsRepository: FavoriteRepositoryContract
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




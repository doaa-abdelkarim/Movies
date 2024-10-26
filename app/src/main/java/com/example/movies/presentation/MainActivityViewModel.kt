package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.repositories.BaseFavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val favoritesRepository: BaseFavoritesRepository,
) : ViewModel() {

    val favorites = MutableStateFlow<List<Favorite>>(emptyList())

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() =
        viewModelScope.launch {
            try {
                favorites.value = favoritesRepository.getAllFavorites()
            } catch (e: Exception) {
                Timber.d(e.localizedMessage)
            }
        }
}




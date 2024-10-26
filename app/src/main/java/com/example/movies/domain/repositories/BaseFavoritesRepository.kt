package com.example.movies.domain.repositories

import com.example.movies.data.local.models.favorites.LocalFavorite
import com.example.movies.domain.entities.Favorite

interface BaseFavoritesRepository {
    suspend fun cacheFavorite(baseLocalFavorite: LocalFavorite)
    suspend fun getAllFavorites(): List<Favorite>
}
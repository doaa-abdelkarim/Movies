package com.example.movies.domain.repositories

import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.domain.entities.BaseVideo

interface BaseFavoriteRepository {
    suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite)
    suspend fun getAllFavorites(): List<BaseVideo>
}
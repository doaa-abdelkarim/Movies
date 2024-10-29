package com.example.movies.data.repositories

import com.example.movies.data.local.db.dao.FavoritesDao
import com.example.movies.data.local.models.LocalFavorite
import com.example.movies.data.local.models.asDomainModel
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.repositories.BaseFavoritesRepository

class FavoritesRepository(
    private val favoritesDao: FavoritesDao
) : BaseFavoritesRepository {
    override suspend fun cacheFavorite(localFavorite: LocalFavorite) {
        favoritesDao.insert(localFavorite)
    }

    override suspend fun getAllFavorites(): List<Favorite> {
        return favoritesDao.getAllFavorites().asDomainModel()
    }
}


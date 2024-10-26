package com.example.movies.data.repositories

import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.videos.asDomainModel
import com.example.movies.domain.entities.BaseVideo
import com.example.movies.domain.repositories.BaseFavoriteRepository

class FavoriteMoviesRepository(
    private val favoriteMoviesDao: FavoriteMoviesDao
) : BaseFavoriteRepository {
    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        favoriteMoviesDao.insert(baseLocalFavorite as LocalFavoriteMovie)
    }

    override suspend fun getAllFavorites(): List<BaseVideo> {
        return favoriteMoviesDao.getAllFavoritesMovie().asDomainModel()
    }
}

